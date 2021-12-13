USE boomber_man;

DROP TABLE IF EXISTS objects;
CREATE TABLE objects
(
	game_id         int unsigned NOT NULL,
	type            varchar(255) NOT NULL,
	x               int unsigned NOT NULL,
	y               int unsigned NOT NULL,
	frame_num       int NOT NULL
);
