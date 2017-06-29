INSERT INTO oauth_client_details (
  client_id,
  client_secret,
  scope,
  resource_ids,
  authorized_grant_types,
  authorities
)
VALUES (
  'testClient',
  'testClientSecret',
  'read_data_points,write_data_points,delete_data_points',
  'dataPoints',
  'authorization_code,implicit,password,refresh_token',
  'ROLE_CLIENT'
);
INSERT INTO oauth_client_details (
  client_id,
  client_secret,
  scope,
  resource_ids,
  authorized_grant_types,
  authorities
)
VALUES (
  'testMedicalPersonnel',
  'testMedicalPersonnelSecret',
  'read_data_points,read_private',
  'dataPoints',
  'authorization_code,implicit,password,refresh_token',
  'ROLE_CLIENT'
);


INSERT INTO oauth_client_details (
  client_id,
  client_secret,
  scope,
  resource_ids,
  authorized_grant_types,
  authorities
)
VALUES (
  'researcher-ui',
  'bff47cb0-9277-4e3b-890f-e9d81f76e5f2',
  'read_data_points,read_surveys,write_surveys,delete_surveys',
  'dataPoints',
  'authorization_code,implicit,password,refresh_token',
  'ROLE_CLIENT'
);

INSERT INTO oauth_client_details (
  client_id,
  client_secret,
  scope,
  resource_ids,
  authorized_grant_types,
  authorities
)
VALUES (
  'cf6abb1a',
  '7b66-4479-97d9-02d3253bb5c5',
  'read_data_points,write_data_points,delete_data_points,read_surveys',
  'dataPoints',
  'authorization_code,implicit,password,refresh_token',
  'ROLE_CLIENT'
);
