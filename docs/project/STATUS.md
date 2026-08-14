# 当前状态

- 日期：2026-08-15
- 阶段：`dsh-android-app` v1.0.0 最终发布刷新
- 当前任务：`REL-004`、`IME-001`、`PARITY-001`
- 插件基线：`dsh-workspace@1.0.0`
- 包名：`io.github.hakunm.deepseekharness`
- Android 基线：min SDK 26、target/compile SDK 36、JDK 17
- 已完成：默认中文/英文切换、正式签名、HTTP/HTTPS 配对、Keystore 令牌、实时聊天、TODO、命令、权限与审批、工作区/会话管理、文件编辑与 Markdown、模型供应商和 Agent/模型/思考强度选择
- 本次刷新：README 使用用户提供的 6 张真实手机截图，补充文件浏览和新建工作区界面；GitHub topics 加入 `dsh-plugin`
- 最近验证：Temurin 17 下 `docsCheck testDebugUnitTest lintDebug assembleRelease` 通过，8 份文档和 31 个任务一致；R8 Release 与专用签名检查通过
- 正式产物：`artifacts/DeepSeek-Harness-v1.0.0.apk`，2,561,512 字节，SHA-256 `80940602D79ACB68D5805122FEC4E6678F39ADEF02DEDAEE4DD7B4856CD7DBBE`
- 签名：APK Signature Scheme v2，单一 Hakunm 4096 位 RSA 证书；证书 SHA-256 `A92938B33F59D90993ADA96437F8A8DEF987A27353E46A018661DDC1587046B1`
- Oracle 演示环境：发布前历史与临时截图会话已删除，只保留一个本次创建的“你好”演示会话；DSH WebUI `3080` 与 listener `3090` 正常
- GitHub：`https://github.com/Hakunm/dsh-android-app`，topic 包含 `dsh-plugin`
- Release：`https://github.com/Hakunm/dsh-android-app/releases/tag/v1.0.0`
- 发布策略：最终 `main` 与 `v1.0.0` 标签只保留一个 v1.0.0 根提交，Release 资产使用本页记录的校验和
- 阻塞项：`PARITY.md` 中仍有后续 DSH WebUI 能力；`IME-001` 继续扩大不同厂商键盘实机覆盖
- 下一步：观察重写后的 GitHub Actions，并继续 HTTPS、键盘和网络切换实机矩阵
