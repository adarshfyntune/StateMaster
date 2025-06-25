
CREATE TABLE cities (
    city_id BIGSERIAL PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL,
    state_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES states(state_id)
);


