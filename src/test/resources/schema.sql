-- ユーザーテーブル作成
CREATE TABLE users
(
userid VARCHAR(24) NOT NULL,
name VARCHAR(50) NOT NULL,
email VARCHAR(255) NOT NULL,
PRIMARY KEY(userid)
);