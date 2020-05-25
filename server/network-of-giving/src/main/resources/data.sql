INSERT INTO users(id, username, password, name, age, gender, location)
values(1, 'username', '$2a$10$vmSIdhkdgbqvIM8ZVMttzOIEDZgR/ibw51a7NE.KR34c04Zf/iDYq',
'User Name', 21, 'MALE', 'Sofia')
ON CONFLICT DO NOTHING;

-- second argument is the BCrypt encoding of the string "password"