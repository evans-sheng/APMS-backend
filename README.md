# 家庭相册后端系统 (Family Album Backend)

[![GPL-3.0 License](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE.md)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.6.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)

一个基于Spring Boot的开源家庭相册管理系统后端，支持照片上传、相册管理、标签分类和缩略图生成等功能。

## 📋 目录

- [功能特性](#-功能特性)
- [技术栈](#-技术栈)
- [系统要求](#-系统要求)
- [快速开始](#-快速开始)
- [配置说明](#-配置说明)
- [API文档](#-api文档)
- [数据库设计](#-数据库设计)
- [项目结构](#-项目结构)
- [开发指南](#-开发指南)
- [部署说明](#-部署说明)
- [许可证](#-许可证)
- [贡献指南](#-贡献指南)

## ✨ 功能特性

### 核心功能
- 📸 **文件管理**：支持多种图片格式（JPG、PNG、GIF、WebP）的上传、删除和查看
- 📚 **相册管理**：创建、编辑、删除相册，支持相册封面设置
- 🏷️ **标签系统**：为照片和相册添加标签，支持标签分类和搜索
- 🖼️ **缩略图生成**：自动生成图片缩略图，提升加载速度
- 🔍 **搜索功能**：支持按文件名、标签等条件搜索
- 📄 **分页查询**：支持大量数据的分页展示

### 技术特性
- 🚀 **高性能**：基于Spring Boot框架，支持高并发访问
- 💾 **数据安全**：MySQL数据库存储，支持事务处理
- 🔒 **参数验证**：完善的输入参数验证和异常处理
- 📊 **日志记录**：详细的操作日志和错误日志
- 🌐 **RESTful API**：标准的REST API设计

## 🛠 技术栈

### 后端框架
- **Spring Boot** 2.6.7 - 主框架
- **MyBatis** - ORM框架
- **Spring Boot Validation** - 参数验证
- **Spring Boot Web** - Web框架

### 数据库
- **MySQL** 8.0+ - 主数据库
- **连接池** - HikariCP（Spring Boot默认）

### 图片处理
- **Thumbnailator** 0.4.17 - 图片缩略图生成
- **Apache Commons IO** - 文件操作工具
- **Apache Commons FileUpload** - 文件上传处理

### 其他组件
- **Lombok** - 简化Java代码
- **Jackson** - JSON处理
- **Logback** - 日志框架
- **Quartz** - 定时任务（可选）

## 📋 系统要求

### 软件要求
- **Java**: JDK 8 或更高版本
- **Maven**: 3.6 或更高版本
- **MySQL**: 8.0 或更高版本

### 硬件要求
- **内存**: 最低 2GB RAM，推荐 4GB+
- **存储**: 根据照片数量确定，建议预留充足空间
- **CPU**: 双核心或更高

## 🚀 快速开始

### 1. 克隆项目
```bash
git clone https://github.com/your-username/family-album-backend.git
cd family-album-backend
```

### 2. 配置数据库
创建MySQL数据库：
```sql
CREATE DATABASE family_album CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

执行数据库初始化脚本：
```bash
mysql -u root -p family_album < src/main/resources/schema.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/family_album?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password

file:
  upload:
    path: /path/to/your/uploads/
    thumbnail-path: /path/to/your/uploads/thumbnails/
```

### 4. 构建并运行
```bash
# 构建项目
mvn clean package

# 运行项目
java -jar target/picture-backend-1.0-SNAPSHOT.jar

# 或者直接运行
mvn spring-boot:run
```

### 5. 验证安装
访问 `http://localhost:8888` 确认服务正常启动。

## ⚙️ 配置说明

### 应用配置
```yaml
server:
  port: 8888                    # 服务端口
  servlet:
    context-path: /             # 应用上下文路径

spring:
  servlet:
    multipart:
      max-file-size: 10MB       # 单个文件最大大小
      max-request-size: 50MB    # 请求最大大小
```

### 文件存储配置
```yaml
file:
  upload:
    path: /uploads/                          # 文件存储路径
    thumbnail-path: /uploads/thumbnails/     # 缩略图存储路径
    allowed-extensions: jpg,jpeg,png,gif,webp  # 允许的文件扩展名
    max-size: 10485760                       # 文件大小限制（字节）
    thumbnail-size: 200                      # 缩略图尺寸
```

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/family_album
    username: root
    password: your_password
    
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.seong.entity
  configuration:
    map-underscore-to-camel-case: true
```

## 📚 API文档

### 文件管理API

#### 上传文件
```http
POST /api/files/upload
Content-Type: multipart/form-data

参数:
- file: 文件对象（必填）
- albumId: 相册ID（可选）
- description: 文件描述（可选）
```

#### 获取文件列表
```http
GET /api/files?page=1&limit=20&albumId=xxx&search=keyword
```

#### 删除文件
```http
DELETE /api/files/{fileId}
```

### 相册管理API

#### 创建相册
```http
POST /api/albums
Content-Type: application/json

{
  "name": "相册名称",
  "description": "相册描述"
}
```

#### 获取相册列表
```http
GET /api/albums?page=1&limit=20
```

#### 更新相册
```http
PUT /api/albums/{albumId}
Content-Type: application/json

{
  "name": "新名称",
  "description": "新描述"
}
```

#### 删除相册
```http
DELETE /api/albums/{albumId}
```

完整API文档请参考：[API接口文档.md](API接口文档.md)

## 🗄️ 数据库设计

### 主要表结构

#### 相册表 (albums)
- `id`: 相册ID（UUID）
- `name`: 相册名称
- `description`: 相册描述
- `cover_photo_id`: 封面照片ID
- `photo_count`: 照片数量
- `created_at`: 创建时间
- `updated_at`: 更新时间

#### 文件表 (files)
- `id`: 文件ID（UUID）
- `name`: 文件名
- `original_name`: 原始文件名
- `size`: 文件大小
- `type`: 文件MIME类型
- `path`: 文件存储路径
- `thumbnail_path`: 缩略图路径
- `album_id`: 所属相册ID

#### 标签表 (tags)
- `id`: 标签ID（UUID）
- `name`: 标签名称
- `color`: 标签颜色

详细数据库设计请参考：[数据库设计.md](数据库设计.md)

## 📁 项目结构

```
src/main/java/com/seong/
├── common/                 # 公共组件
│   ├── exception/         # 异常处理
│   ├── PageResult.java    # 分页结果
│   └── Result.java        # 统一响应结果
├── config/                # 配置类
│   └── FileConfig.java    # 文件配置
├── controller/            # 控制器层
│   ├── AlbumController.java
│   └── FileInfoController.java
├── dto/                   # 数据传输对象
│   └── request/          # 请求DTO
├── entity/               # 实体类
│   ├── Album.java
│   ├── FileInfo.java
│   └── Tag.java
├── mapper/               # 数据访问层
├── service/              # 业务逻辑层
│   └── impl/            # 业务实现
└── utils/                # 工具类
    ├── FileUtils.java
    ├── ThumbnailUtils.java
    └── UUIDUtils.java
```

## 👨‍💻 开发指南

### 开发环境搭建
1. 安装 JDK 8+
2. 安装 Maven 3.6+
3. 安装 MySQL 8.0+
4. 安装 IDE（推荐 IntelliJ IDEA）

### 代码规范
- 使用Lombok简化代码
- 遵循阿里巴巴Java开发手册
- 使用驼峰命名法
- 添加必要的注释

### 测试
```bash
# 运行单元测试
mvn test

# 运行集成测试
mvn integration-test
```

### 调试
- 开发环境默认开启debug日志
- 可通过日志查看SQL执行和文件操作
- 支持IDE断点调试

## 🚀 部署说明

### Docker部署（推荐）
```bash
# 构建镜像
docker build -t family-album-backend .

# 运行容器
docker run -d \
  --name family-album-backend \
  -p 8888:8888 \
  -v /path/to/uploads:/uploads \
  -e MYSQL_URL=jdbc:mysql://mysql:3306/family_album \
  -e MYSQL_USERNAME=root \
  -e MYSQL_PASSWORD=password \
  family-album-backend
```

### 传统部署
1. 构建JAR包：`mvn clean package`
2. 上传到服务器
3. 配置数据库和文件存储路径
4. 启动应用：`java -jar picture-backend-1.0-SNAPSHOT.jar`

### Nginx配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        proxy_pass http://localhost:8888;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
    
    # 静态文件直接服务
    location /uploads/ {
        alias /path/to/uploads/;
    }
}
```

## 📄 许可证

本项目采用 [GNU General Public License v3.0](LICENSE.md) 开源协议。

### 主要条款
- ✅ 可以自由使用、修改、分发
- ✅ 可以用于商业用途
- ❗ 必须开源修改后的代码
- ❗ 必须保留版权声明
- ❗ 衍生作品必须使用相同许可证

## 🤝 贡献指南

我们欢迎所有形式的贡献！

### 如何贡献
1. Fork 本项目
2. 创建特性分支：`git checkout -b feature/amazing-feature`
3. 提交更改：`git commit -m 'Add amazing feature'`
4. 推送到分支：`git push origin feature/amazing-feature`
5. 提交 Pull Request

### 报告问题
如果您发现了bug或有功能建议，请通过以下方式报告：
- 在GitHub上创建Issue
- 详细描述问题或建议
- 提供复现步骤（如果是bug）

### 开发规范
- 遵循现有代码风格
- 添加适当的测试
- 更新相关文档
- 确保所有测试通过

## 📞 联系我们

- **项目主页**: https://github.com/your-username/family-album-backend
- **问题反馈**: https://github.com/your-username/family-album-backend/issues
- **邮箱**: your-email@example.com

## 🙏 致谢

感谢以下开源项目：
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis](https://mybatis.org/)
- [Thumbnailator](https://github.com/coobird/thumbnailator)
- [MySQL](https://www.mysql.com/)

## 📊 项目状态

- ✅ 基础功能完成
- ✅ API文档完善
- ✅ 数据库设计完成
- 🚧 前端界面开发中
- 📋 计划添加用户认证
- 📋 计划添加批量操作

---

**⭐ 如果这个项目对您有帮助，请给我们一个Star！** 