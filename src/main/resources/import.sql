INSERT INTO `users` (username, password, enabled) VALUES ('josue', '$2a$10$fQtJcrvc1CygUUT5/K0GounLLsqJP0aYjBDAVHhIgrfpPwAuPMoaK', 1);
INSERT INTO `users` (username, password, enabled) VALUES ('admin', '$2a$10$uzpFwIuL96csw4PVE7WjrOnX5Ai/0o.EMGRpNYBXsLSuG9yA4e1Ce', 1);

INSERT INTO `authorities` (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES(2, 'ROLE_ADMIN');
INSERT INTO `authorities` (user_id, authority) VALUES(2, 'ROLE_USER');