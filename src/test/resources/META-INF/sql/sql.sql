
CREATE TABLE `cms_template` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
   `file_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
   `content` text COLLATE utf8_unicode_ci NOT NULL,
   `sort` int(11) NOT NULL DEFAULT '0',
   `update_time` datetime NOT NULL,
   PRIMARY KEY (`id`)
 ) 