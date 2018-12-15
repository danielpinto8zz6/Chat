# SQL command to create the table: 
# Remember to correct VARCHAR column lengths to proper values 
# and add additional indexes for your own extensions.

# If you had prepaired CREATE TABLE SQL-statement before, 
# make sure that this automatically generated code is 
# compatible with your own code. If SQL code is incompatible,
# it is not possible to use these generated sources successfully.
# (Changing VARCHAR column lenghts will not break code.)

CREATE TABLE users (
      username varchar(255) NOT NULL,
      password varchar(255),
      state bigint,
      tcp_port bigint,
      udp_port bigint,
      address varchar(255),
      files varchar(255),
      failures bigint,
PRIMARY KEY(username),
INDEX users_username_INDEX (username))
CHARACTER SET 'utf8';