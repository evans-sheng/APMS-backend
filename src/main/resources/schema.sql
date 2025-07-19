CREATE TABLE `albums` (
	`id` VARCHAR(36) NOT NULL COMMENT '相册ID' COLLATE 'utf8mb4_unicode_ci',
	`name` VARCHAR(100) NOT NULL COMMENT '相册名称' COLLATE 'utf8mb4_unicode_ci',
	`description` TEXT NULL DEFAULT NULL COMMENT '相册描述' COLLATE 'utf8mb4_unicode_ci',
	`cover_photo_id` VARCHAR(36) NULL DEFAULT NULL COMMENT '封面照片ID' COLLATE 'utf8mb4_unicode_ci',
	`photo_count` INT(11) NULL DEFAULT '0' COMMENT '照片数量',
	`created_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `idx_albums_composite` (`deleted_at`, `created_at`) USING BTREE
)
COMMENT='相册表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `album_tags` (
	`id` VARCHAR(36) NOT NULL COMMENT '关联ID' COLLATE 'utf8mb4_unicode_ci',
	`album_id` VARCHAR(36) NOT NULL COMMENT '相册ID' COLLATE 'utf8mb4_unicode_ci',
	`tag_id` VARCHAR(36) NOT NULL COMMENT '标签ID' COLLATE 'utf8mb4_unicode_ci',
	`created_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uk_album_tag` (`album_id`, `tag_id`) USING BTREE,
	INDEX `idx_album_id` (`album_id`) USING BTREE,
	INDEX `idx_tag_id` (`tag_id`) USING BTREE,
	CONSTRAINT `album_tags_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT `album_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
)
COMMENT='相册标签关联表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `favorite_album` (
	`albumId` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`createTime` DATETIME NULL DEFAULT NULL
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `favorite_file` (
	`fileId` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
	`createTime` DATETIME NULL DEFAULT NULL
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `files` (
	`id` VARCHAR(36) NOT NULL COMMENT '文件ID' COLLATE 'utf8mb4_unicode_ci',
	`name` VARCHAR(255) NOT NULL COMMENT '文件名' COLLATE 'utf8mb4_unicode_ci',
	`original_name` VARCHAR(255) NOT NULL COMMENT '原始文件名' COLLATE 'utf8mb4_unicode_ci',
	`size` BIGINT(20) NOT NULL COMMENT '文件大小(字节)',
	`type` VARCHAR(100) NOT NULL COMMENT '文件类型' COLLATE 'utf8mb4_unicode_ci',
	`extension` VARCHAR(20) NOT NULL COMMENT '文件扩展名' COLLATE 'utf8mb4_unicode_ci',
	`path` VARCHAR(500) NOT NULL COMMENT '文件路径' COLLATE 'utf8mb4_unicode_ci',
	`thumbnail_path` VARCHAR(500) NULL DEFAULT NULL COMMENT '缩略图路径' COLLATE 'utf8mb4_unicode_ci',
	`description` TEXT NULL DEFAULT NULL COMMENT '文件描述' COLLATE 'utf8mb4_unicode_ci',
	`album_id` VARCHAR(36) NULL DEFAULT NULL COMMENT '所属相册ID' COLLATE 'utf8mb4_unicode_ci',
	`url` VARCHAR(500) NULL DEFAULT NULL COMMENT '访问URL' COLLATE 'utf8mb4_unicode_ci',
	`thumbnail_url` VARCHAR(500) NULL DEFAULT NULL COMMENT '缩略图URL' COLLATE 'utf8mb4_unicode_ci',
	`created_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`deleted_at` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `idx_album_id` (`album_id`) USING BTREE,
	INDEX `idx_type` (`type`) USING BTREE,
	INDEX `idx_created_at` (`created_at`) USING BTREE,
	INDEX `idx_deleted_at` (`deleted_at`) USING BTREE,
	INDEX `idx_files_composite` (`album_id`, `deleted_at`, `created_at`) USING BTREE,
	CONSTRAINT `files_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`) ON UPDATE NO ACTION ON DELETE SET NULL
)
COMMENT='文件表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `file_tags` (
	`id` VARCHAR(36) NOT NULL COMMENT '关联ID' COLLATE 'utf8mb4_unicode_ci',
	`file_id` VARCHAR(36) NOT NULL COMMENT '文件ID' COLLATE 'utf8mb4_unicode_ci',
	`tag_id` VARCHAR(36) NOT NULL COMMENT '标签ID' COLLATE 'utf8mb4_unicode_ci',
	`created_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `uk_file_tag` (`file_id`, `tag_id`) USING BTREE,
	INDEX `idx_file_id` (`file_id`) USING BTREE,
	INDEX `idx_tag_id` (`tag_id`) USING BTREE,
	CONSTRAINT `file_tags_ibfk_1` FOREIGN KEY (`file_id`) REFERENCES `files` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
	CONSTRAINT `file_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
)
COMMENT='文件标签关联表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `tags` (
	`id` VARCHAR(36) NOT NULL COMMENT '标签ID' COLLATE 'utf8mb4_unicode_ci',
	`name` VARCHAR(50) NOT NULL COMMENT '标签名称' COLLATE 'utf8mb4_unicode_ci',
	`color` VARCHAR(7) NULL DEFAULT NULL COMMENT '标签颜色' COLLATE 'utf8mb4_unicode_ci',
	`created_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `name` (`name`) USING BTREE,
	INDEX `idx_name` (`name`) USING BTREE
)
COMMENT='标签表'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;



































