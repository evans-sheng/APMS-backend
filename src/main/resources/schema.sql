-- 家庭相册网站数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS family_album DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE family_album;

-- 相册表
CREATE TABLE IF NOT EXISTS albums (
    id VARCHAR(36) PRIMARY KEY COMMENT '相册ID',
    name VARCHAR(100) NOT NULL COMMENT '相册名称',
    description TEXT COMMENT '相册描述',
    cover_photo_id VARCHAR(36) COMMENT '封面照片ID',
    photo_count INT DEFAULT 0 COMMENT '照片数量',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='相册表';

-- 文件表
CREATE TABLE IF NOT EXISTS files (
    id VARCHAR(36) PRIMARY KEY COMMENT '文件ID',
    name VARCHAR(255) NOT NULL COMMENT '文件名',
    original_name VARCHAR(255) NOT NULL COMMENT '原始文件名',
    size BIGINT NOT NULL COMMENT '文件大小(字节)',
    type VARCHAR(100) NOT NULL COMMENT '文件类型',
    extension VARCHAR(20) NOT NULL COMMENT '文件扩展名',
    path VARCHAR(500) NOT NULL COMMENT '文件路径',
    thumbnail_path VARCHAR(500) COMMENT '缩略图路径',
    description TEXT COMMENT '文件描述',
    album_id VARCHAR(36) COMMENT '所属相册ID',
    url VARCHAR(500) COMMENT '访问URL',
    thumbnail_url VARCHAR(500) COMMENT '缩略图URL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at TIMESTAMP NULL COMMENT '删除时间',
    KEY idx_album_id (album_id),
    KEY idx_type (type),
    KEY idx_created_at (created_at),
    KEY idx_deleted_at (deleted_at),
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';

-- 标签表
CREATE TABLE IF NOT EXISTS tags (
    id VARCHAR(36) PRIMARY KEY COMMENT '标签ID',
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
    color VARCHAR(7) COMMENT '标签颜色',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 相册标签关联表
CREATE TABLE IF NOT EXISTS album_tags (
    id VARCHAR(36) PRIMARY KEY COMMENT '关联ID',
    album_id VARCHAR(36) NOT NULL COMMENT '相册ID',
    tag_id VARCHAR(36) NOT NULL COMMENT '标签ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_album_tag (album_id, tag_id),
    KEY idx_album_id (album_id),
    KEY idx_tag_id (tag_id),
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='相册标签关联表';

-- 文件标签关联表
CREATE TABLE IF NOT EXISTS file_tags (
    id VARCHAR(36) PRIMARY KEY COMMENT '关联ID',
    file_id VARCHAR(36) NOT NULL COMMENT '文件ID',
    tag_id VARCHAR(36) NOT NULL COMMENT '标签ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_file_tag (file_id, tag_id),
    KEY idx_file_id (file_id),
    KEY idx_tag_id (tag_id),
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件标签关联表';

-- 用户表（可选）
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(500) COMMENT '头像URL',
    role VARCHAR(20) DEFAULT 'USER' COMMENT '用户角色',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '用户状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_username (username),
    KEY idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 分享链接表（可选）
CREATE TABLE IF NOT EXISTS share_links (
    id VARCHAR(36) PRIMARY KEY COMMENT '分享链接ID',
    share_code VARCHAR(32) NOT NULL UNIQUE COMMENT '分享码',
    album_id VARCHAR(36) COMMENT '相册ID',
    file_id VARCHAR(36) COMMENT '文件ID',
    password VARCHAR(20) COMMENT '访问密码',
    expire_time TIMESTAMP COMMENT '过期时间',
    view_count INT DEFAULT 0 COMMENT '访问次数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_share_code (share_code),
    KEY idx_album_id (album_id),
    KEY idx_file_id (file_id),
    FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分享链接表';

-- 插入初始数据
INSERT INTO albums (id, name, description, photo_count) VALUES 
('default-album-001', '默认相册', '系统默认相册，用于存放未分类的照片', 0),
('family-album-001', '家庭相册', '记录家庭美好时光的照片集合', 0);

INSERT INTO tags (id, name, color) VALUES 
('tag-001', '风景', '#4CAF50'),
('tag-002', '人物', '#2196F3'),
('tag-003', '美食', '#FF9800'),
('tag-004', '旅行', '#9C27B0'),
('tag-005', '节日', '#F44336');

-- 创建索引优化查询性能
CREATE INDEX idx_files_composite ON files(album_id, deleted_at, created_at);
CREATE INDEX idx_albums_composite ON albums(deleted_at, created_at); 