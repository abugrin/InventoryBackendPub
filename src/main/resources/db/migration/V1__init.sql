CREATE TABLE contact
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(100) NULL,
    phone       VARCHAR(100) NULL,
    email       VARCHAR(100) NULL,
    location_id INT NOT NULL,
    CONSTRAINT pk_contact PRIMARY KEY (id)
);

CREATE TABLE device
(
    id            INT AUTO_INCREMENT NOT NULL,
    pn            VARCHAR(100) NULL,
    serial        VARCHAR(50) NULL,
    name          VARCHAR(100) NOT NULL,
    `description` VARCHAR(200) NULL,
    create_date   datetime     NOT NULL,
    update_date   datetime     NOT NULL,
    update_user   INT          NOT NULL,
    type_id       INT          NOT NULL,
    status_id     INT          NOT NULL,
    location_id   INT          NOT NULL,
    CONSTRAINT pk_device PRIMARY KEY (id)
);

CREATE TABLE device_type
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_device_type PRIMARY KEY (id)
);

CREATE TABLE eq_status
(
    id   INT         NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_eq_status PRIMARY KEY (id)
);

INSERT INTO `eq_status` (`id`, `name`) VALUES
                                           (0, 'Исправно'),
                                           (1, 'Неисправно'),
                                           (2, 'В ремонте'),
                                           (3, 'Списано'),
                                           (4, 'Проверка');

CREATE TABLE event
(
    id            INT AUTO_INCREMENT NOT NULL,
    time          datetime NOT NULL,
    action        BIGINT   NOT NULL,
    object        INT      NOT NULL,
    `description` VARCHAR(45) NULL,
    device        INT NULL,
    part          INT NULL,
    location_from INT NULL,
    location_to   INT NULL,
    user          INT      NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE event_action
(
    id   BIGINT      NOT NULL,
    name VARCHAR(45) NOT NULL,
    CONSTRAINT pk_event_action PRIMARY KEY (id)
);

INSERT INTO `event_action` (`id`, `name`) VALUES
                                              (1, 'add'),
                                              (2, 'change'),
                                              (3, 'move');

CREATE TABLE event_object
(
    id   INT NOT NULL,
    name VARCHAR(45) NULL,
    CONSTRAINT pk_event_object PRIMARY KEY (id)
);

INSERT INTO `event_object` (`id`, `name`) VALUES
                                              (0, 'generic'),
                                              (1, 'user'),
                                              (2, 'device'),
                                              (3, 'part'),
                                              (4, 'location');

CREATE TABLE location
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(100) NOT NULL,
    address     VARCHAR(300) NOT NULL,
    type_id     INT          NOT NULL,
    create_date datetime     NOT NULL,
    update_date datetime     NOT NULL,
    update_user INT          NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE location_type
(
    id   INT         NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_location_type PRIMARY KEY (id)
);

INSERT INTO `location_type` (`id`, `name`) VALUES
                                               (0, 'Склад'),
                                               (1, 'Заказчик'),
                                               (2, 'Партнер');

CREATE TABLE part
(
    id            INT AUTO_INCREMENT NOT NULL,
    pn            VARCHAR(100) NULL,
    serial        VARCHAR(50) NULL,
    name          VARCHAR(100) NOT NULL,
    `description` VARCHAR(200) NULL,
    create_date   datetime     NOT NULL,
    update_date   datetime     NOT NULL,
    update_user   INT          NOT NULL,
    device_id     INT NULL,
    status_id     INT          NOT NULL,
    location_id   INT          NOT NULL,
    type_id       INT          NOT NULL,
    CONSTRAINT pk_part PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(100) NULL,
    password VARCHAR(500) NULL,
    enabled  BOOLEAN NOT NULL,
    name     VARCHAR(255) NULL,
    email    VARCHAR(50) NULL,
    picture  BLOB NULL,
    CONSTRAINT pk_users_id PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE KEY (username)
);

INSERT INTO `users` (`username`, `password`, `enabled`, `name`, `email`) VALUES
    ('admin', '$2y$10$lRYbGaHZ2PCUxOPvUEmzk.D0GslWL21RXrbvmJKrs4XeNVpLUw3Ka', true, 'Admin', 'admin@localhost');

CREATE TABLE authorities
(
    id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(50) NOT NULL ,
    authority VARCHAR(50) NOT NULL ,
    CONSTRAINT pk_auth_id PRIMARY KEY (id),
    constraint FK_AUTHORITIES_USERS FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO authorities (username, authority) VALUES
    ('admin', 'ROLE_ADMIN'),
    ('admin', 'ROLE_USER');

ALTER TABLE contact
    ADD CONSTRAINT FK_CONTACT_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE device
    ADD CONSTRAINT FK_DEVICE_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE device
    ADD CONSTRAINT FK_DEVICE_ON_STATUS FOREIGN KEY (status_id) REFERENCES eq_status (id);

ALTER TABLE device
    ADD CONSTRAINT FK_DEVICE_ON_TYPE FOREIGN KEY (type_id) REFERENCES device_type (id);

ALTER TABLE device
    ADD CONSTRAINT FK_DEVICE_ON_UPDATE_USER FOREIGN KEY (update_user) REFERENCES users (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_ACTION FOREIGN KEY (action) REFERENCES event_action (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_DEVICE FOREIGN KEY (device) REFERENCES device (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_LOCATION_FROM FOREIGN KEY (location_from) REFERENCES location (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_LOCATION_TO FOREIGN KEY (location_to) REFERENCES location (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_OBJECT FOREIGN KEY (object) REFERENCES event_object (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_PART FOREIGN KEY (part) REFERENCES part (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (user) REFERENCES users (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_TYPE FOREIGN KEY (type_id) REFERENCES location_type (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_UPDATE_USER FOREIGN KEY (update_user) REFERENCES users (id);

ALTER TABLE part
    ADD CONSTRAINT FK_PART_ON_DEVICE FOREIGN KEY (device_id) REFERENCES device (id);

ALTER TABLE part
    ADD CONSTRAINT FK_PART_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE part
    ADD CONSTRAINT FK_PART_ON_STATUS FOREIGN KEY (status_id) REFERENCES eq_status (id);

ALTER TABLE part
    ADD CONSTRAINT FK_PART_ON_TYPE FOREIGN KEY (type_id) REFERENCES device_type (id);

ALTER TABLE part
    ADD CONSTRAINT FK_PART_ON_UPDATE_USER FOREIGN KEY (update_user) REFERENCES users (id);

