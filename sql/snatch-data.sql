
INSERT INTO `snatch`.`leader` (leader_name) VALUES 
('Master Sly'),
('Rogue Paw'),
('Shadow Fang');

SELECT * FROM leader;

INSERT INTO `snatch`.`captain` (captain_name, leader_id) VALUES 
('Captain Whiskers', 1), 
('Captain Paws', 2), 
('Captain Midnight', 3);

SELECT * FROM captain;


INSERT INTO `snatch`.`crew` (crew_name, max_capacity, availability, has_captain, captain_id) 
VALUES 
('Shadow Squad', 5, TRUE, TRUE, 1), 
('Silent Paws', 6, TRUE, TRUE, 2), 
('Midnight Marauders', 4, FALSE, TRUE, 3);

SELECT * FROM crew;

INSERT INTO `snatch`.`personnel` 
(personnel_name, species, profile_img, is_assigned, crew_id) 
VALUES 
('Sneaky', 'Raccoon', 'sneaky.webp', TRUE, 1), 
('Claws', 'Fox', 'claws.webp', TRUE, 1), 
('Swift', 'Hawk', 'swift.webp', TRUE, 2), 
('Shadow', 'Panther', 'shadow.webp', FALSE, null), 
('Digger', 'Mole', 'digger.webp', TRUE, 2),
('Bart', 'Pangolin', 'bart.webp', False, null);

SELECT * FROM personnel;

INSERT INTO `snatch`.`skill` (skill_name) 
VALUES 
('Lockpicking'), 
('Stealth'), 
('Climbing'), 
('Digging'), 
('Reconnaissance'), 
('Trap Disarming'), 
('Speed'), 
('Agility');

SELECT * FROM skill;

INSERT INTO `snatch`.`personnel_skill` (personnel_id, skill_id) 
VALUES 
-- Sneaky (personnel_id = 1)
(1, 1), -- Lockpicking
(1, 2), -- Stealth

-- Claws (personnel_id = 2)
(2, 3), -- Climbing
(2, 2), -- Stealth

-- Swift (personnel_id = 3)
(3, 5), -- Reconnaissance
(3, 7), -- Speed

-- Shadow (personnel_id = 4)
(4, 2), -- Stealth
(4, 6), -- Trap Disarming

-- Digger (personnel_id = 5)
(5, 4), -- Digging
(5, 8), -- Agility

-- Bart (personnel_id = 6)
(6, 3),
(6, 6);

SELECT * FROM personnel_skill;

INSERT INTO `snatch`.`heist` (description, location, is_assigned, status, crew_id) 
VALUES 
('Steal gold bars from the downtown bank vault', 'Downtown Bank', TRUE, 'In Progress', 1), 
('Retrieve the ancient artifact from the museum', 'City Museum', TRUE, 'Planned', 2), 
('Rescue stolen treasure from rival gang', 'Forest Hideout', TRUE, 'Completed', 3);

SELECT * FROM heist;

INSERT INTO `snatch`.`loot` (item_name, quantity) 
VALUES 
('Gold Bar', 20), 
('Diamond', 15), 
('Ancient Artifact', 2), 
('Jewel Necklace', 10), 
('Classified Documents', 5), 
('Rare Painting', 1), 
('Cash Bundle', 50), 
('Emerald Ring', 8);

SELECT * FROM loot;

