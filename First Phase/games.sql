USE boomber_man;

DROP TABLE IF EXISTS games;
CREATE TABLE games
(
	id              int unsigned NOT NULL auto_increment, # Unique ID for the record
	name            varchar(255) NOT NULL,
	lev             int unsigned NOT NULL,
	time            int unsigned NOT NULL,
	score           int unsigned NOT NULL,
	map_x           int unsigned NOT NULL,
	map_y           int unsigned NOT NULL,
	guy_speed       int unsigned NOT NULL,
	door_I          int unsigned NOT NULL,
	door_J          int unsigned NOT NULL,
	radius          int unsigned NOT NULL,
	boomb_lim       int unsigned NOT NULL,
	is_ghost        int unsigned NOT NULL,
	remote          int unsigned NOT NULL,
	PRIMARY KEY     (id)
);
