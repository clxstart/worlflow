---
name: dual-push-setup
description: 同时推送到 GitHub 和 Gitee 双远程仓库的配置方法
type: reference
---

## 同时推送到 GitHub 和 Gitee

配置 git 同时推送到两个远程仓库：

### 初始化并添加双远程

```bash
# 初始化仓库
git init

# 添加第一个远程仓库 (fetch 和 push)
git remote add origin https://gitee.com/clxri/worlflow.git

# 添加第二个推送地址
git remote set-url --add --push origin https://gitee.com/clxri/worlflow.git
git remote set-url --add --push origin https://github.com/clxstart/worlflow.git
```

### 验证配置

```bash
git remote -v
```

输出应显示：
```
origin  https://gitee.com/clxri/worlflow.git (fetch)
origin  https://gitee.com/clxri/worlflow.git (push)
origin  https://github.com/clxstart/worlflow.git (push)
```

### 推送

```bash
git add .
git commit -m "your message"
git push -u origin master
```

一次 `git push` 会同时推送到两个仓库。

### 注意事项

- GitHub SSH 推送需要配置 SSH 密钥，建议使用 HTTPS 格式避免权限问题
- 如需修改推送地址，使用 `git remote set-url --delete --push origin <old_url>` 删除旧地址后再添加新地址