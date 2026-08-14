# Android 项目恢复入口

每轮先读 `STATUS.md`、`ROADMAP.md` 和 `PARITY.md`，涉及界面时同时读取 `../../design-system/deepseek-harness-android/MASTER.md`。App 只使用插件公开版本化 API；发现能力不足时先在插件仓库更新 API、OpenAPI/AsyncAPI、SDK、scope 和合约测试，再接入 App。

完成任务、架构决定、测试结论或能力矩阵变化时同步更新本文档目录。签名文件、密码、设备令牌和服务器正文不得进入文档或 Git。

每次提交前运行 `./gradlew docsCheck`；该任务校验恢复文档、路线图状态与 `STATUS.md` 任务引用，Windows 可使用 `gradlew.bat docsCheck`。
