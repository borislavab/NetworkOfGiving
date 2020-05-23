INSERT INTO users(id, username, password)
values(1, 'username', '$2a$10$vmSIdhkdgbqvIM8ZVMttzOIEDZgR/ibw51a7NE.KR34c04Zf/iDYq')
ON CONFLICT DO NOTHING;

-- second argument is the BCrypt encoding of the string "password"