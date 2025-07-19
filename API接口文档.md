# 家庭相册网站后端接口文档

## 概述

本文档描述了家庭相册网站前端调用的后端接口。后端服务运行在 `localhost:8888`，提供文件管理和相册管理功能。

## 基础信息

- **服务器地址**: `http://localhost:8888`
- **基础路径**: `/api/files`
- **完整基础URL**: `http://localhost:8888/api/files`
- **请求超时**: 30秒
- **支持的文件格式**: jpg, jpeg, png, gif, webp
- **最大文件大小**: 10MB (10485760 bytes)

## 通用响应格式

### 成功响应
```json
{
  "success": true,
  "data": {},
  "message": "操作成功"
}
```

### 错误响应
```json
{
  "success": false,
  "error": "错误信息",
  "code": "ERROR_CODE"
}
```

## 文件管理接口

### 1. 获取文件列表

**接口地址**: `GET /api/files/list`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | number | 否 | 页码，默认1 |
| limit | number | 否 | 每页数量，默认20 |
| albumId | string | 否 | 相册ID，筛选指定相册的文件 |
| search | string | 否 | 搜索关键词 |
| sortBy | string | 否 | 排序字段：name, size, createdAt |
| sortOrder | string | 否 | 排序方向：asc, desc |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "files": [
      {
        "id": "file_123",
        "name": "family_photo.jpg",
        "size": 1024000,
        "type": "image/jpeg",
        "albumId": "album_456",
        "createdAt": "2024-01-01T12:00:00Z",
        "updatedAt": "2024-01-01T12:00:00Z"
      }
    ],
    "total": 100,
    "page": 1,
    "limit": 20
  }
}
```

### 2. 获取单个文件

**接口地址**: `GET /api/files/query/{fileId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "file_123",
    "name": "family_photo.jpg",
    "size": 1024000,
    "type": "image/jpeg",
    "albumId": "album_456",
    "url": "http://localhost:8888/api/files/file_123",
    "thumbnailUrl": "http://localhost:8888/api/files/file_123?type=thumbnail",
    "createdAt": "2024-01-01T12:00:00Z",
    "updatedAt": "2024-01-01T12:00:00Z"
  }
}
```

### 3. 上传单个文件

**接口地址**: `POST /api/files/upload`

**请求类型**: `multipart/form-data`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | File | 是 | 要上传的文件 |
| albumId | string | 否 | 相册ID，将文件添加到指定相册 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "file_123",
    "name": "family_photo.jpg",
    "size": 1024000,
    "type": "image/jpeg",
    "albumId": "album_456",
    "url": "http://localhost:8888/api/files/file_123",
    "thumbnailUrl": "http://localhost:8888/api/files/file_123?type=thumbnail",
    "createdAt": "2024-01-01T12:00:00Z"
  }
}
```

### 4. 批量上传文件

**接口地址**: `POST /api/files/upload/batch`

**请求类型**: `multipart/form-data`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| files | File[] | 是 | 要上传的文件数组 |
| albumId | string | 否 | 相册ID，将文件添加到指定相册 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "uploaded": [
      {
        "id": "file_123",
        "name": "photo1.jpg",
        "size": 1024000,
        "type": "image/jpeg",
        "albumId": "album_456",
        "url": "http://localhost:8888/api/files/file_123",
        "thumbnailUrl": "http://localhost:8888/api/files/file_123?type=thumbnail",
        "createdAt": "2024-01-01T12:00:00Z"
      }
    ],
    "failed": [],
    "total": 1,
    "successCount": 1,
    "failCount": 0
  }
}
```

### 5. 删除文件

**接口地址**: `DELETE /api/files/delete/{fileId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "deleted": true,
    "fileId": "file_123"
  },
  "message": "文件删除成功"
}
```

### 6. 批量删除文件

**接口地址**: `DELETE /api/files/batch`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileIds | string[] | 是 | 要删除的文件ID数组 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "deleted": ["file_123", "file_456"],
    "failed": [],
    "total": 2,
    "successCount": 2,
    "failCount": 0
  },
  "message": "批量删除成功"
}
```

### 7. 更新文件信息

**接口地址**: `PUT /api/files/update/{fileId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | string | 否 | 文件名 |
| albumId | string | 否 | 相册ID |
| description | string | 否 | 文件描述 |
| tags | string[] | 否 | 标签数组 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "file_123",
    "name": "updated_photo.jpg",
    "size": 1024000,
    "type": "image/jpeg",
    "albumId": "album_456",
    "description": "家庭聚会照片",
    "tags": ["家庭", "聚会"],
    "updatedAt": "2024-01-01T12:00:00Z"
  },
  "message": "文件信息更新成功"
}
```

### 8. 获取文件缩略图

**接口地址**: `GET /api/files/getFile/{fileId}?type=thumbnail`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**查询参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | string | 是 | 固定值：thumbnail |

**响应**: 返回缩略图文件流

### 9. 获取原图

**接口地址**: `GET /api/files/getFile/{fileId}?type=original`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**查询参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | string | 是 | 固定值：original |

**响应**: 返回原图文件流

## 相册管理接口

### 1. 获取相册列表

**接口地址**: `GET /api/files/albums`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | number | 否 | 页码，默认1 |
| limit | number | 否 | 每页数量，默认20 |
| search | string | 否 | 搜索关键词 |
| sortBy | string | 否 | 排序字段：name, createdAt, photoCount |
| sortOrder | string | 否 | 排序方向：asc, desc |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "albums": [
      {
        "id": "album_123",
        "name": "家庭聚会",
        "description": "2024年家庭聚会照片",
        "photoCount": 25,
        "coverPhoto": "file_456",
        "tags": ["家庭", "聚会"],
        "createdAt": "2024-01-01T12:00:00Z",
        "updatedAt": "2024-01-01T12:00:00Z"
      }
    ],
    "total": 10,
    "page": 1,
    "limit": 20
  }
}
```

### 2. 获取相册详情

**接口地址**: `GET /api/files/albums/{albumId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "album_123",
    "name": "家庭聚会",
    "description": "2024年家庭聚会照片",
    "photoCount": 25,
    "coverPhoto": {
      "id": "file_456",
      "name": "cover.jpg",
      "thumbnailUrl": "http://localhost:8888/api/files/file_456?type=thumbnail"
    },
    "tags": ["家庭", "聚会"],
    "createdAt": "2024-01-01T12:00:00Z",
    "updatedAt": "2024-01-01T12:00:00Z"
  }
}
```

### 3. 创建相册

**接口地址**: `POST /api/files/albums`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | string | 是 | 相册名称 |
| description | string | 否 | 相册描述 |
| tags | string[] | 否 | 标签数组 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "album_123",
    "name": "新相册",
    "description": "相册描述",
    "photoCount": 0,
    "coverPhoto": null,
    "tags": ["标签1", "标签2"],
    "createdAt": "2024-01-01T12:00:00Z",
    "updatedAt": "2024-01-01T12:00:00Z"
  },
  "message": "相册创建成功"
}
```

### 4. 更新相册

**接口地址**: `PUT /api/files/albums/{albumId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | string | 否 | 相册名称 |
| description | string | 否 | 相册描述 |
| tags | string[] | 否 | 标签数组 |
| coverPhoto | string | 否 | 封面照片文件ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "album_123",
    "name": "更新后的相册名",
    "description": "更新后的描述",
    "photoCount": 25,
    "coverPhoto": "file_789",
    "tags": ["新标签"],
    "updatedAt": "2024-01-01T12:00:00Z"
  },
  "message": "相册更新成功"
}
```

### 5. 删除相册

**接口地址**: `DELETE /api/files/albums/{albumId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "deleted": true,
    "albumId": "album_123",
    "deletedPhotos": 25
  },
  "message": "相册删除成功"
}
```

### 6. 获取相册内的照片

**接口地址**: `GET /api/files/albums/{albumId}/photos`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | number | 否 | 页码，默认1 |
| limit | number | 否 | 每页数量，默认20 |
| search | string | 否 | 搜索关键词 |
| sortBy | string | 否 | 排序字段：name, size, createdAt |
| sortOrder | string | 否 | 排序方向：asc, desc |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "photos": [
      {
        "id": "file_123",
        "name": "photo1.jpg",
        "size": 1024000,
        "type": "image/jpeg",
        "albumId": "album_456",
        "url": "http://localhost:8888/api/files/file_123",
        "thumbnailUrl": "http://localhost:8888/api/files/file_123?type=thumbnail",
        "createdAt": "2024-01-01T12:00:00Z"
      }
    ],
    "total": 25,
    "page": 1,
    "limit": 20
  }
}
```

## 收藏文件管理接口

### 1. 收藏文件

**接口地址**: `POST /api/files/favorite/{fileId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**响应示例**:
```json
{
  "success": true,
  "data": true,
  "message": "收藏成功"
}
```

### 2. 取消收藏文件

**接口地址**: `DELETE /api/files/favorite/{fileId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileId | string | 是 | 文件ID |

**响应示例**:
```json
{
  "success": true,
  "data": true,
  "message": "取消收藏成功"
}
```

## 收藏相册管理接口

### 1. 收藏相册

**接口地址**: `POST /api/files/album/favorite/{albumId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**响应示例**:
```json
{
  "success": true,
  "data": true,
  "message": "收藏成功"
}
```

### 2. 取消收藏相册

**接口地址**: `DELETE /api/files/album/favorite/{albumId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| albumId | string | 是 | 相册ID |

**响应示例**:
```json
{
  "success": true,
  "data": true,
  "message": "取消收藏成功"
}
```

## 标签管理接口

### 1. 创建标签

**接口地址**: `POST /api/files/tags`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | string | 是 | 标签名称 |
| description | string | 否 | 标签描述 |
| color | string | 否 | 标签颜色 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "tag_123",
    "name": "家庭",
    "description": "家庭相关照片",
    "color": "#FF5722",
    "createdAt": "2024-01-01T12:00:00Z",
    "updatedAt": "2024-01-01T12:00:00Z"
  },
  "message": "标签创建成功"
}
```

### 2. 获取标签列表（分页）

**接口地址**: `GET /api/files/tags`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | number | 否 | 页码，默认1 |
| limit | number | 否 | 每页数量，默认20 |
| search | string | 否 | 搜索关键词 |
| sortBy | string | 否 | 排序字段：name, createdAt |
| sortOrder | string | 否 | 排序方向：asc, desc |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "list": [
      {
        "id": "tag_123",
        "name": "家庭",
        "description": "家庭相关照片",
        "color": "#FF5722",
        "createdAt": "2024-01-01T12:00:00Z",
        "updatedAt": "2024-01-01T12:00:00Z"
      }
    ],
    "total": 10,
    "page": 1,
    "limit": 20
  }
}
```

### 3. 获取所有标签

**接口地址**: `GET /api/files/tags/all`

**响应示例**:
```json
{
  "success": true,
  "data": [
    {
      "id": "tag_123",
      "name": "家庭",
      "description": "家庭相关照片",
      "color": "#FF5722",
      "createdAt": "2024-01-01T12:00:00Z",
      "updatedAt": "2024-01-01T12:00:00Z"
    }
  ]
}
```

### 4. 根据ID获取标签信息

**接口地址**: `GET /api/files/tags/{tagId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| tagId | string | 是 | 标签ID |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "tag_123",
    "name": "家庭",
    "description": "家庭相关照片",
    "color": "#FF5722",
    "createdAt": "2024-01-01T12:00:00Z",
    "updatedAt": "2024-01-01T12:00:00Z"
  }
}
```

### 5. 更新标签信息

**接口地址**: `PUT /api/files/tags/{tagId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| tagId | string | 是 | 标签ID |

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | string | 否 | 标签名称 |
| description | string | 否 | 标签描述 |
| color | string | 否 | 标签颜色 |

**响应示例**:
```json
{
  "success": true,
  "data": {
    "id": "tag_123",
    "name": "更新后的标签名",
    "description": "更新后的描述",
    "color": "#2196F3",
    "updatedAt": "2024-01-01T12:00:00Z"
  },
  "message": "标签更新成功"
}
```

### 6. 删除标签

**接口地址**: `DELETE /api/files/tags/{tagId}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| tagId | string | 是 | 标签ID |

**响应示例**:
```json
{
  "success": true,
  "data": true,
  "message": "标签删除成功"
}
```

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 未授权访问 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 413 | 文件过大 |
| 415 | 不支持的文件格式 |
| 500 | 服务器内部错误 |

## 注意事项

1. **文件上传限制**:
   - 最大文件大小：10MB
   - 支持格式：jpg, jpeg, png, gif, webp
   - 上传超时：30秒

2. **分页参数**:
   - 默认每页20条记录
   - 页码从1开始

3. **文件访问**:
   - 原图访问：`/api/files/{fileId}?type=original`
   - 缩略图访问：`/api/files/{fileId}?type=thumbnail`

4. **批量操作**:
   - 批量上传和删除支持进度回调
   - 批量操作会返回成功和失败的详细信息

5. **安全性**:
   - 所有接口都需要进行适当的权限验证
   - 文件上传需要进行格式和大小验证
   - 建议在生产环境中启用HTTPS

## 环境配置

前端项目通过环境变量配置后端服务地址：

```bash
# 开发环境 (.env.development)
VITE_FILE_SERVER_HOST=localhost
VITE_FILE_SERVER_PORT=8888
VITE_FILE_SERVER_PROTOCOL=http
VITE_FILE_SERVER_BASE_URL=/api/files

# 生产环境 (.env.production)
VITE_FILE_SERVER_HOST=your-domain.com
VITE_FILE_SERVER_PORT=443
VITE_FILE_SERVER_PROTOCOL=https
VITE_FILE_SERVER_BASE_URL=/api/files
```

---

**文档版本**: 1.0  
**最后更新**: 2024年1月  
**维护者**: 开发团队 