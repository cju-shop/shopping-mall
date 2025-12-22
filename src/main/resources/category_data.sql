-- 부모 카테고리 (없으면 삽입)
INSERT INTO category (name, is_active, parent_id)
SELECT 'outerwear', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='outerwear' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'tops', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='tops' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'pants', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='pants' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'dress', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='dress' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'skirt', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='skirt' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'shoes', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='shoes' AND parent_id IS NULL);

INSERT INTO category (name, is_active, parent_id)
SELECT 'bag', 1, NULL
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='bag' AND parent_id IS NULL);


-- 자식 카테고리 (부모 id를 서브쿼리로 찾아서 없으면 삽입)
INSERT INTO category (name, is_active, parent_id)
SELECT 'jacket', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='jacket'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

INSERT INTO category (name, is_active, parent_id)
SELECT 'coat', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='coat'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

INSERT INTO category (name, is_active, parent_id)
SELECT 'jumper', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='jumper'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

INSERT INTO category (name, is_active, parent_id)
SELECT 'mouton_fur', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='mouton_fur'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

INSERT INTO category (name, is_active, parent_id)
SELECT 'cardigan', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='cardigan'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

INSERT INTO category (name, is_active, parent_id)
SELECT 'field_jacket', 1, (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
    WHERE NOT EXISTS (
  SELECT 1 FROM category
  WHERE name='field_jacket'
    AND parent_id = (SELECT id FROM category WHERE name='outerwear' AND parent_id IS NULL)
);

-- tops
INSERT INTO category (name, is_active, parent_id)
SELECT 'tshirt', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='tshirt'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'shirt', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='shirt'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'blouse', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='blouse'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'knit_sweater', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='knit_sweater'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'hoodie', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='hoodie'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'sweatshirt', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='sweatshirt'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'sleeveless', 1, (SELECT id FROM category WHERE name='tops' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='sleeveless'
  AND parent_id=(SELECT id FROM category WHERE name='tops' AND parent_id IS NULL));

-- pants
INSERT INTO category (name, is_active, parent_id)
SELECT 'jeans', 1, (SELECT id FROM category WHERE name='pants' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='jeans'
  AND parent_id=(SELECT id FROM category WHERE name='pants' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'slacks', 1, (SELECT id FROM category WHERE name='pants' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='slacks'
  AND parent_id=(SELECT id FROM category WHERE name='pants' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'cotton_pants', 1, (SELECT id FROM category WHERE name='pants' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='cotton_pants'
  AND parent_id=(SELECT id FROM category WHERE name='pants' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'shorts', 1, (SELECT id FROM category WHERE name='pants' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='shorts'
  AND parent_id=(SELECT id FROM category WHERE name='pants' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'training_jogger', 1, (SELECT id FROM category WHERE name='pants' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='training_jogger'
  AND parent_id=(SELECT id FROM category WHERE name='pants' AND parent_id IS NULL));

-- dress
INSERT INTO category (name, is_active, parent_id)
SELECT 'dress_mini', 1, (SELECT id FROM category WHERE name='dress' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='dress_mini'
  AND parent_id=(SELECT id FROM category WHERE name='dress' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'dress_midi', 1, (SELECT id FROM category WHERE name='dress' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='dress_midi'
  AND parent_id=(SELECT id FROM category WHERE name='dress' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'dress_long', 1, (SELECT id FROM category WHERE name='dress' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='dress_long'
  AND parent_id=(SELECT id FROM category WHERE name='dress' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'two_piece', 1, (SELECT id FROM category WHERE name='dress' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='two_piece'
  AND parent_id=(SELECT id FROM category WHERE name='dress' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'jumpsuit', 1, (SELECT id FROM category WHERE name='dress' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='jumpsuit'
  AND parent_id=(SELECT id FROM category WHERE name='dress' AND parent_id IS NULL));

-- skirt
INSERT INTO category (name, is_active, parent_id)
SELECT 'skirt_mini', 1, (SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='skirt_mini'
  AND parent_id=(SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'skirt_midi', 1, (SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='skirt_midi'
  AND parent_id=(SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'skirt_long', 1, (SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='skirt_long'
  AND parent_id=(SELECT id FROM category WHERE name='skirt' AND parent_id IS NULL));

-- shoes
INSERT INTO category (name, is_active, parent_id)
SELECT 'flat_loafer', 1, (SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='flat_loafer'
  AND parent_id=(SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'sandal_slipper', 1, (SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='sandal_slipper'
  AND parent_id=(SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'heel', 1, (SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='heel'
  AND parent_id=(SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'sneakers', 1, (SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='sneakers'
  AND parent_id=(SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'boots_worker', 1, (SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='boots_worker'
  AND parent_id=(SELECT id FROM category WHERE name='shoes' AND parent_id IS NULL));

-- bag
INSERT INTO category (name, is_active, parent_id)
SELECT 'crossbag', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='crossbag'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'totebag', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='totebag'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'shoulderbag', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='shoulderbag'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'ecobag', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='ecobag'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'clutch', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='clutch'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'backpack', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='backpack'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'wallet', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='wallet'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
INSERT INTO category (name, is_active, parent_id)
SELECT 'pouch', 1, (SELECT id FROM category WHERE name='bag' AND parent_id IS NULL)
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name='pouch'
  AND parent_id=(SELECT id FROM category WHERE name='bag' AND parent_id IS NULL));
