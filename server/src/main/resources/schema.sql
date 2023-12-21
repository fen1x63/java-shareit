DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS item_requests CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_requests (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  description VARCHAR(2000) NOT NULL,
  requestor_id INTEGER NOT NULL,
  created TIMESTAMP NOT NULL,
  CONSTRAINT fk_item_requests_to_users FOREIGN KEY(requestor_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS items (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  description VARCHAR(500) NOT NULL,
  is_available BOOLEAN NOT NULL,
  owner INTEGER NOT NULL,
  request_id INTEGER,
  CONSTRAINT fk_items_to_users FOREIGN KEY(owner) REFERENCES users(id),
  CONSTRAINT fk_items_to_item_requests FOREIGN KEY(request_id) REFERENCES item_requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP NOT NULL,
  item_id INTEGER NOT NULL,
  booker_id INTEGER NOT NULL,
  status VARCHAR(8),
  CONSTRAINT fk_bookings_to_users FOREIGN KEY(booker_id) REFERENCES users(id),
  CONSTRAINT fk_bookings_to_items FOREIGN KEY(item_id) REFERENCES items(id)
);

CREATE TABLE IF NOT EXISTS comments (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  text VARCHAR(500) NOT NULL,
  item_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL,
  created TIMESTAMP NOT NULL,
  CONSTRAINT fk_comments_to_users FOREIGN KEY(author_id) REFERENCES users(id),
  CONSTRAINT fk_comments_to_items FOREIGN KEY(item_id) REFERENCES items(id)
);