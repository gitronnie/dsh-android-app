import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

abstract class DocsCheckTask : DefaultTask() {
    @get:InputDirectory
    abstract val docsDirectory: DirectoryProperty

    @TaskAction
    fun checkDocuments() {
        val directory = docsDirectory.get()
        val required = listOf(
            "README.md",
            "STATUS.md",
            "ROADMAP.md",
            "PARITY.md",
            "ARCHITECTURE.md",
            "SECURITY.md",
            "TESTING.md",
            "CHANGELOG-DEV.md",
        )
        val missing = required.filterNot { directory.file(it).asFile.isFile }
        check(missing.isEmpty()) { "Missing project documents: ${missing.joinToString()}" }

        val roadmap = directory.file("ROADMAP.md").asFile.readText()
        val tasks = Regex("""\| ([A-Z]+-\d{3}) \| ([a-z-]+) \|""")
            .findAll(roadmap)
            .associate { it.groupValues[1] to it.groupValues[2] }
        check(tasks.isNotEmpty()) { "ROADMAP.md contains no task rows." }
        val validStatuses = setOf("planned", "in-progress", "blocked", "done")
        val invalid = tasks.filterValues { it !in validStatuses }
        check(invalid.isEmpty()) { "Invalid task statuses: $invalid" }

        val status = directory.file("STATUS.md").asFile.readText()
        val unknown = Regex("""`([A-Z]+-\d{3})`""").findAll(status)
            .map { it.groupValues[1] }
            .filterNot(tasks::containsKey)
            .toSet()
        check(unknown.isEmpty()) { "STATUS.md references unknown tasks: ${unknown.joinToString()}" }
        println("docsCheck: ${required.size} documents and ${tasks.size} tasks are consistent")
    }
}

tasks.register<DocsCheckTask>("docsCheck") {
    group = "verification"
    description = "Checks the persistent Android project tracking documents."
    docsDirectory.set(layout.projectDirectory.dir("docs/project"))
}
