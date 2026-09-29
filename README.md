# 36.5°数字温控系统 — Android V14

本工程直接封装 V14 Web UI，并针对安卓展示终端做了处理。

## 本版重点
- 120 帧旋转模特不再依赖超长 base64：120 张 WebP 已拆分到 `assets/media/frames/`，由本地文件逐帧播放，提升 Android WebView 稳定性。
- 4 张热力图已拆分为本地 PNG，轮播仍由页面 JavaScript 定时执行。
- 模特区与热力图区启用页面级双指缩放/拖动；WebView 自身整页缩放被关闭，避免抢占手势。
- 横屏、沉浸式全屏、保持屏幕常亮。
- 保留 `BOOT_COMPLETED / LOCKED_BOOT_COMPLETED` 开机自启接收器。
- GitHub Actions 已配置，可直接构建 debug APK。

## GitHub 生成 APK
1. 将本工程所有文件上传到 GitHub 仓库根目录。
2. 打开 **Actions → Build Android APK → Run workflow**，或直接 push 到 main/master。
3. 构建完成后，在该次 Actions 页面底部下载 `ThermalSuitDisplay-V14-debug` artifact，里面就是 `app-debug.apk`。

## 安卓实机交互
- 模特：自动 30fps 旋转；点击暂停/继续；双指缩放；单指拖动；“重置”恢复。
- 热力图：约 1.2 秒自动切换四视角；点击视角按钮暂停到指定视角；双指缩放；单指拖动；点击热力图可暂停/继续轮播。

## 开机自启说明
工程已经包含开机广播自启代码。但 Android 10+ 及部分国产/商用系统会限制“后台直接拉起 Activity”。若设备系统拦截，需要在设备系统设置里允许本应用“自启动/后台启动”，或将应用配置为设备的 kiosk/Device Owner 应用。代码侧的开机接收器已保留。

## 关键文件
- `app/src/main/assets/index.html`：V14 UI 与交互逻辑
- `app/src/main/assets/media/frames/`：120 帧旋转模特
- `app/src/main/assets/media/thermal_*.png`：四视角热力图
- `MainActivity.kt`：WebView、全屏、常亮与 Android 运行设置
- `BootReceiver.kt`：开机自启
- `.github/workflows/build-apk.yml`：GitHub 自动构建 APK

## V15 精简上传版
- 120 个逐帧 WebP 已合并为单个本地 `model_rotation.mp4`，GitHub 网页上传不再出现上百个资源文件。
- 模特视频仍支持自动循环、暂停/播放、重置，以及双指缩放和放大后拖动。
- 4 张热力图保留为本地 PNG，继续自动轮播、按钮切换、双指缩放和拖动。
- 所有展示媒体均为 APK 本地资源，不依赖网络。
- 开机自启、横屏沉浸式、常亮功能保持不变。
