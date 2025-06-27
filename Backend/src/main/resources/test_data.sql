use nimn;

-- Food 테스트 데이터 생성 SQL (50개) - 실제 테이블명에 맞게 수정
-- GPT 비용 절약을 위한 로컬 테스트용 데이터
-- 가격대: 4000원, 5500원, 7000원

-- 1. NutritionFact 테이블에 영양성분 데이터 삽입 (50개)
INSERT INTO t_nutrition_fact (calories, carbohydrate, protein, fat, sugar, sodium) VALUES
-- 4000원대 메뉴 (20개)
('380kcal', '55g', '15g', '8g', '5g', '650mg'),     -- 1. 라면
('420kcal', '65g', '12g', '10g', '6g', '700mg'),    -- 2. 김밥
('350kcal', '50g', '14g', '9g', '4g', '600mg'),     -- 3. 주먹밥
('390kcal', '58g', '16g', '8g', '7g', '680mg'),     -- 4. 떡볶이
('450kcal', '70g', '18g', '12g', '8g', '750mg'),    -- 5. 볶음밥
('320kcal', '45g', '12g', '7g', '3g', '580mg'),     -- 6. 국수
('410kcal', '60g', '17g', '9g', '6g', '720mg'),     -- 7. 잔치국수
('380kcal', '55g', '15g', '8g', '5g', '650mg'),     -- 8. 콩나물국밥
('440kcal', '68g', '16g', '11g', '7g', '780mg'),    -- 9. 비빔국수
('360kcal', '52g', '13g', '8g', '4g', '620mg'),     -- 10. 어묵국
('400kcal', '62g', '14g', '9g', '6g', '690mg'),     -- 11. 만두국
('430kcal', '65g', '17g', '10g', '8g', '740mg'),    -- 12. 육개장라면
('370kcal', '54g', '12g', '8g', '5g', '640mg'),     -- 13. 우동
('420kcal', '63g', '15g', '9g', '7g', '710mg'),     -- 14. 떡국
('390kcal', '57g', '14g', '8g', '6g', '670mg'),     -- 15. 김치라면
('410kcal', '61g', '16g', '9g', '6g', '700mg'),     -- 16. 팟타이
('380kcal', '56g', '13g', '8g', '5g', '660mg'),     -- 17. 쌀국수
('350kcal', '51g', '11g', '7g', '4g', '590mg'),     -- 18. 미역국
('440kcal', '67g', '15g', '11g', '8g', '760mg'),    -- 19. 볶음우동
('400kcal', '59g', '14g', '9g', '6g', '680mg'),     -- 20. 순대국

-- 5500원대 메뉴 (15개)
('520kcal', '68g', '22g', '14g', '8g', '850mg'),    -- 21. 된장찌개정식
('580kcal', '75g', '25g', '16g', '10g', '920mg'),   -- 22. 김치찌개정식
('550kcal', '70g', '24g', '15g', '9g', '880mg'),    -- 23. 순두부찌개정식
('600kcal', '78g', '26g', '17g', '11g', '950mg'),   -- 24. 부대찌개정식
('480kcal', '62g', '20g', '12g', '7g', '800mg'),    -- 25. 미역국정식
('560kcal', '72g', '23g', '15g', '9g', '890mg'),    -- 26. 콩나물국정식
('590kcal', '76g', '25g', '16g', '10g', '930mg'),   -- 27. 계란찜정식
('540kcal', '69g', '22g', '14g', '8g', '870mg'),    -- 28. 시금치된장국정식
('610kcal', '79g', '26g', '17g', '11g', '960mg'),   -- 29. 열무국수정식
('500kcal', '64g', '21g', '13g', '7g', '820mg'),    -- 30. 냉면
('570kcal', '73g', '24g', '15g', '9g', '900mg'),    -- 31. 물냉면
('620kcal', '80g', '27g', '18g', '12g', '980mg'),   -- 32. 비빔냉면
('490kcal', '63g', '20g', '12g', '7g', '810mg'),    -- 33. 온면
('550kcal', '71g', '23g', '14g', '8g', '880mg'),    -- 34. 막국수
('580kcal', '74g', '25g', '16g', '10g', '910mg'),   -- 35. 메밀국수

-- 7000원대 메뉴 (15개)
('720kcal', '85g', '32g', '22g', '12g', '1100mg'),  -- 36. 불고기정식
('780kcal', '92g', '35g', '25g', '15g', '1200mg'),  -- 37. 제육볶음정식
('680kcal', '78g', '28g', '19g', '10g', '1000mg'),  -- 38. 생선구이정식
('820kcal', '95g', '38g', '28g', '18g', '1300mg'),  -- 39. 돈까스정식
('750kcal', '88g', '33g', '23g', '13g', '1150mg'),  -- 40. 치킨까스정식
('700kcal', '82g', '30g', '20g', '11g', '1050mg'),  -- 41. 함박스테이크정식
('760kcal', '89g', '34g', '24g', '14g', '1170mg'),  -- 42. 닭갈비정식
('690kcal', '80g', '29g', '19g', '10g', '1020mg'),  -- 43. 오징어볶음정식
('740kcal', '86g', '32g', '22g', '12g', '1130mg'),  -- 44. 두부김치정식
('710kcal', '83g', '31g', '21g', '11g', '1080mg'),  -- 45. 계란말이정식
('800kcal', '94g', '36g', '26g', '16g', '1250mg'),  -- 46. 갈비탕정식
('660kcal', '76g', '27g', '18g', '9g', '980mg'),    -- 47. 비빔밥정식
('730kcal', '85g', '32g', '22g', '12g', '1120mg'),  -- 48. 쌈밥정식
('770kcal', '90g', '34g', '24g', '14g', '1180mg'),  -- 49. 삼겹살정식
('720kcal', '84g', '31g', '21g', '11g', '1100mg');  -- 50. 닭볶음탕정식

-- 2. Food 테이블에 음식 데이터 삽입 (50개)
INSERT INTO t_food (name, image, price, main1, main2, side1, side2, side3, nutrition_fact_id) VALUES
-- 4000원대 메뉴 (20개)
('라면', '/api/image/test1', '4000원', '라면', '계란', '김치', '단무지', '파', 1),
('참치김밥', '/api/image/test2', '4000원', '참치김밥', '단무지', '우엉조림', '시금치나물', '계란말이', 2),
('주먹밥', '/api/image/test3', '4000원', '주먹밥', '김가루', '참치마요', '단무지', '김치', 3),
('떡볶이', '/api/image/test4', '4000원', '떡볶이', '어묵', '삶은계란', '오뎅국물', '김말이', 4),
('새우볶음밥', '/api/image/test5', '4000원', '새우볶음밥', '계란스크램블', '단무지', '김치', '미역국', 5),
('멸치국수', '/api/image/test6', '4000원', '소면', '멸치육수', '파', '김치', '단무지', 6),
('잔치국수', '/api/image/test7', '4000원', '잔치국수', '멸치육수', '파', '김치', '단무지', 7),
('콩나물국밥', '/api/image/test8', '4000원', '콩나물국', '밥', '김치', '깍두기', '계란', 8),
('비빔국수', '/api/image/test9', '4000원', '비빔국수', '고추장양념', '오이채', '삶은계란', '김치', 9),
('어묵국', '/api/image/test10', '4000원', '어묵국', '밥', '김치', '깍두기', '파', 10),
('만두국', '/api/image/test11', '4000원', '만두국', '밥', '김치', '깍두기', '파', 11),
('육개장라면', '/api/image/test12', '4000원', '육개장라면', '계란', '김치', '단무지', '파', 12),
('우동', '/api/image/test13', '4000원', '우동', '어묵', '파', '김치', '단무지', 13),
('떡국', '/api/image/test14', '4000원', '떡국', '만두', '김치', '깍두기', '계란지단', 14),
('김치라면', '/api/image/test15', '4000원', '김치라면', '계란', '파', '김치', '단무지', 15),
('팟타이', '/api/image/test16', '4000원', '쌀국수', '팟타이소스', '숙주나물', '라임', '땅콩가루', 16),
('베트남쌀국수', '/api/image/test17', '4000원', '쌀국수', '육수', '숙주나물', '고수', '라임', 17),
('미역국밥', '/api/image/test18', '4000원', '미역국', '밥', '김치', '깍두기', '참기름', 18),
('볶음우동', '/api/image/test19', '4000원', '볶음우동', '야채', '어묵', '김치', '단무지', 19),
('순대국', '/api/image/test20', '4000원', '순대국', '밥', '김치', '깍두기', '새우젓', 20),

-- 5500원대 메뉴 (15개)
('된장찌개정식', '/api/image/test21', '5500원', '된장찌개', '밥', '김치', '시금치나물', '계란후라이', 21),
('김치찌개정식', '/api/image/test22', '5500원', '김치찌개', '밥', '깍두기', '콩나물무침', '계란말이', 22),
('순두부찌개정식', '/api/image/test23', '5500원', '순두부찌개', '밥', '김치', '시금치나물', '계란찜', 23),
('부대찌개정식', '/api/image/test24', '5500원', '부대찌개', '밥', '김치', '콩나물무침', '계란', 24),
('미역국정식', '/api/image/test25', '5500원', '미역국', '밥', '김치', '시금치나물', '계란후라이', 25),
('콩나물국정식', '/api/image/test26', '5500원', '콩나물국', '밥', '김치', '무생채', '계란말이', 26),
('계란찜정식', '/api/image/test27', '5500원', '계란찜', '밥', '김치', '시금치나물', '미역국', 27),
('시금치된장국정식', '/api/image/test28', '5500원', '시금치된장국', '밥', '김치', '콩나물무침', '계란후라이', 28),
('열무국수정식', '/api/image/test29', '5500원', '열무국수', '밥', '김치', '깍두기', '계란지단', 29),
('물냉면', '/api/image/test30', '5500원', '물냉면', '냉면육수', '오이', '계란', '배', 30),
('비빔냉면', '/api/image/test31', '5500원', '비빔냉면', '고추장양념', '오이', '계란', '배', 31),
('온면', '/api/image/test32', '5500원', '온면', '따뜻한육수', '파', '김치', '단무지', 32),
('막국수', '/api/image/test33', '5500원', '막국수', '비빔양념', '오이', '계란', '김치', 33),
('메밀국수', '/api/image/test34', '5500원', '메밀국수', '육수', '파', '김치', '단무지', 34),
('함흥냉면', '/api/image/test35', '5500원', '냉면', '냉면육수', '오이', '계란', '배', 35),

-- 7000원대 메뉴 (15개)
('불고기정식', '/api/image/test36', '7000원', '불고기', '밥', '김치', '콩나물무침', '된장국', 36),
('제육볶음정식', '/api/image/test37', '7000원', '제육볶음', '밥', '상추쌈', '콩나물무침', '된장국', 37),
('생선구이정식', '/api/image/test38', '7000원', '고등어구이', '밥', '김치', '콩나물무침', '된장국', 38),
('돈까스정식', '/api/image/test39', '7000원', '등심돈까스', '양배추샐러드', '밥', '김치', '된장국', 39),
('치킨까스정식', '/api/image/test40', '7000원', '치킨까스', '양배추샐러드', '밥', '김치', '콘스프', 40),
('함박스테이크정식', '/api/image/test41', '7000원', '함박스테이크', '양파소스', '밥', '샐러드', '콘스프', 41),
('닭갈비정식', '/api/image/test42', '7000원', '닭갈비', '밥', '상추쌈', '콩나물무침', '된장국', 42),
('오징어볶음정식', '/api/image/test43', '7000원', '오징어볶음', '밥', '상추쌈', '콩나물무침', '된장국', 43),
('두부김치정식', '/api/image/test44', '7000원', '두부김치', '밥', '미역국', '계란찜', '오이무침', 44),
('계란말이정식', '/api/image/test45', '7000원', '계란말이', '밥', '김치', '시금치나물', '된장국', 45),
('갈비탕정식', '/api/image/test46', '7000원', '갈비탕', '밥', '김치', '오이무침', '계란찜', 46),
('비빔밥정식', '/api/image/test47', '7000원', '비빔밥', '고추장', '나물5종', '계란후라이', '된장국', 47),
('쌈밥정식', '/api/image/test48', '7000원', '쌈밥', '쌈장', '상추쌈', '고기볶음', '된장국', 48),
('삼겹살정식', '/api/image/test49', '7000원', '삼겹살', '밥', '상추쌈', '콩나물무침', '된장국', 49),
('닭볶음탕정식', '/api/image/test50', '7000원', '닭볶음탕', '밥', '김치', '콩나물무침', '계란찜', 50);

-- 3. ReviewSummary 테이블에 기본 데이터 삽입 (초기값으로 0.0, 0개 설정)
-- @MapsId 사용으로 id 컬럼은 자동으로 food_id와 매핑됨
INSERT INTO t_review_summary (food_id, average_rating, total_reviews) VALUES
(1, 0.0, 0), (2, 0.0, 0), (3, 0.0, 0), (4, 0.0, 0), (5, 0.0, 0),
(6, 0.0, 0), (7, 0.0, 0), (8, 0.0, 0), (9, 0.0, 0), (10, 0.0, 0),
(11, 0.0, 0), (12, 0.0, 0), (13, 0.0, 0), (14, 0.0, 0), (15, 0.0, 0),
(16, 0.0, 0), (17, 0.0, 0), (18, 0.0, 0), (19, 0.0, 0), (20, 0.0, 0),
(21, 0.0, 0), (22, 0.0, 0), (23, 0.0, 0), (24, 0.0, 0), (25, 0.0, 0),
(26, 0.0, 0), (27, 0.0, 0), (28, 0.0, 0), (29, 0.0, 0), (30, 0.0, 0),
(31, 0.0, 0), (32, 0.0, 0), (33, 0.0, 0), (34, 0.0, 0), (35, 0.0, 0),
(36, 0.0, 0), (37, 0.0, 0), (38, 0.0, 0), (39, 0.0, 0), (40, 0.0, 0),
(41, 0.0, 0), (42, 0.0, 0), (43, 0.0, 0), (44, 0.0, 0), (45, 0.0, 0),
(46, 0.0, 0), (47, 0.0, 0), (48, 0.0, 0), (49, 0.0, 0), (50, 0.0, 0);

-- 이번 주 식단 선택지 생성 SQL
-- 기간: 2025년 6월 23일(월) ~ 2025년 6월 29일(일)

-- 1. WeeklyFoodPlan 생성 (1주일 식단 선택지) - 정확한 테이블명 사용
INSERT INTO t_weekly_food_plan (start_date, end_date) VALUES
('2025-06-23', '2025-06-29');

-- WeeklyFoodPlan ID는 1번으로 가정 (AUTO_INCREMENT)

-- 2. DailyFoodPlan 생성 (7일간) - WeeklyFoodPlan과 연결
-- daily_food_plans_id로 수정 (실제 JPA에서 생성되는 FK 컬럼명)
INSERT INTO t_daily_food_plan (day, daily_food_plans_id) VALUES
('2025-06-23', 1), -- 월요일
('2025-06-24', 1), -- 화요일
('2025-06-25', 1), -- 수요일
('2025-06-26', 1), -- 목요일
('2025-06-27', 1), -- 금요일
('2025-06-28', 1), -- 토요일
('2025-06-29', 1); -- 일요일

-- DailyFoodPlan ID는 1~7번으로 가정

-- 3. FoodChoiceSet 생성 (각 날짜별 아침/점심/저녁 = 7일 × 3식 = 21개)
INSERT INTO t_food_choice_set (food_time) VALUES
-- 월요일 (2025-06-23)
('Breakfast'), -- ID: 1
('Lunch'),     -- ID: 2
('Dinner'),    -- ID: 3

-- 화요일 (2025-06-24)
('Breakfast'), -- ID: 4
('Lunch'),     -- ID: 5
('Dinner'),    -- ID: 6

-- 수요일 (2025-06-25)
('Breakfast'), -- ID: 7
('Lunch'),     -- ID: 8
('Dinner'),    -- ID: 9

-- 목요일 (2025-06-26)
('Breakfast'), -- ID: 10
('Lunch'),     -- ID: 11
('Dinner'),    -- ID: 12

-- 금요일 (2025-06-27)
('Breakfast'), -- ID: 13
('Lunch'),     -- ID: 14
('Dinner'),    -- ID: 15

-- 토요일 (2025-06-28)
('Breakfast'), -- ID: 16
('Lunch'),     -- ID: 17
('Dinner'),    -- ID: 18

-- 일요일 (2025-06-29)
('Breakfast'), -- ID: 19
('Lunch'),     -- ID: 20
('Dinner');    -- ID: 21

-- 4. DailyFoodPlan과 FoodChoiceSet 연결 (중간테이블)
INSERT INTO t_daily_food_plan_food_choice_set (daily_food_plan_id, food_choice_set_id) VALUES
-- 월요일 (daily_food_plan_id: 1)
(1, 1), (1, 2), (1, 3),
-- 화요일 (daily_food_plan_id: 2)
(2, 4), (2, 5), (2, 6),
-- 수요일 (daily_food_plan_id: 3)
(3, 7), (3, 8), (3, 9),
-- 목요일 (daily_food_plan_id: 4)
(4, 10), (4, 11), (4, 12),
-- 금요일 (daily_food_plan_id: 5)
(5, 13), (5, 14), (5, 15),
-- 토요일 (daily_food_plan_id: 6)
(6, 16), (6, 17), (6, 18),
-- 일요일 (daily_food_plan_id: 7)
(7, 19), (7, 20), (7, 21);

-- 5. FoodChoiceSet에 음식 3개씩 배정 (각 식사시간마다 3가지 선택지)
INSERT INTO t_food_choice_set_food (food_choice_set_id, food_id) VALUES
-- 월요일 아침 (FoodChoiceSet ID: 1) - 가벼운 메뉴
(1, 1), (1, 3), (1, 6),    -- 라면, 주먹밥, 멸치국수
-- 월요일 점심 (FoodChoiceSet ID: 2) - 든든한 메뉴
(2, 21), (2, 36), (2, 30), -- 된장찌개정식, 불고기정식, 물냉면
-- 월요일 저녁 (FoodChoiceSet ID: 3) - 다양한 메뉴
(3, 39), (3, 22), (3, 15), -- 돈까스정식, 김치찌개정식, 김치라면

-- 화요일 아침 (FoodChoiceSet ID: 4)
(4, 2), (4, 7), (4, 10),   -- 참치김밥, 잔치국수, 어묵국
-- 화요일 점심 (FoodChoiceSet ID: 5)
(5, 25), (5, 37), (5, 33), -- 미역국정식, 제육볶음정식, 막국수
-- 화요일 저녁 (FoodChoiceSet ID: 6)
(6, 40), (6, 23), (6, 12), -- 치킨까스정식, 순두부찌개정식, 육개장라면

-- 수요일 아침 (FoodChoiceSet ID: 7)
(7, 4), (7, 8), (7, 13),   -- 떡볶이, 콩나물국밥, 우동
-- 수요일 점심 (FoodChoiceSet ID: 8)
(8, 26), (8, 38), (8, 31), -- 콩나물국정식, 생선구이정식, 비빔냉면
-- 수요일 저녁 (FoodChoiceSet ID: 9)
(9, 41), (9, 24), (9, 16), -- 함박스테이크정식, 부대찌개정식, 팟타이

-- 목요일 아침 (FoodChoiceSet ID: 10)
(10, 5), (10, 9), (10, 14), -- 새우볶음밥, 비빔국수, 떡국
-- 목요일 점심 (FoodChoiceSet ID: 11)
(11, 27), (11, 42), (11, 32), -- 계란찜정식, 닭갈비정식, 온면
-- 목요일 저녁 (FoodChoiceSet ID: 12)
(12, 46), (12, 28), (12, 18), -- 갈비탕정식, 시금치된장국정식, 미역국

-- 금요일 아침 (FoodChoiceSet ID: 13)
(13, 11), (13, 17), (13, 19), -- 만두국, 베트남쌀국수, 볶음우동
-- 금요일 점심 (FoodChoiceSet ID: 14)
(14, 29), (14, 43), (14, 34), -- 열무국수정식, 오징어볶음정식, 메밀국수
-- 금요일 저녁 (FoodChoiceSet ID: 15)
(15, 47), (15, 35), (15, 20), -- 비빔밥정식, 함흥냉면, 순대국

-- 토요일 아침 (FoodChoiceSet ID: 16)
(16, 7), (16, 3), (16, 8),  -- 잔치국수, 주먹밥, 콩나물국밥
-- 토요일 점심 (FoodChoiceSet ID: 17)
(17, 44), (17, 22), (17, 30), -- 두부김치정식, 김치찌개정식, 물냉면
-- 토요일 저녁 (FoodChoiceSet ID: 18)
(18, 48), (18, 36), (18, 31), -- 쌈밥정식, 불고기정식, 비빔냉면

-- 일요일 아침 (FoodChoiceSet ID: 19)
(19, 1), (19, 6), (19, 11),  -- 라면, 멸치국수, 만두국
-- 일요일 점심 (FoodChoiceSet ID: 20)
(20, 49), (20, 25), (20, 33), -- 삼겹살정식, 미역국정식, 막국수
-- 일요일 저녁 (FoodChoiceSet ID: 21)
(21, 50), (21, 45), (21, 4);  -- 닭볶음탕정식, 계란말이정식, 떡볶이

-- 확인용 쿼리
-- 일주일 식단 선택지 전체 조회
SELECT 
    wfp.start_date, wfp.end_date,
    dfp.day,
    fcs.food_time,
    f.name as food_name, f.price
FROM t_weekly_food_plan wfp
JOIN t_daily_food_plan dfp ON wfp.id = dfp.daily_food_plans_id
JOIN t_daily_food_plan_food_choice_set dfp_fcs ON dfp.id = dfp_fcs.daily_food_plan_id
JOIN t_food_choice_set fcs ON dfp_fcs.food_choice_set_id = fcs.id
JOIN t_food_choice_set_food fcs_f ON fcs.id = fcs_f.food_choice_set_id
JOIN t_food f ON fcs_f.food_id = f.id
ORDER BY dfp.day, 
         CASE fcs.food_time 
             WHEN 'Breakfast' THEN 1 
             WHEN 'Lunch' THEN 2 
             WHEN 'Dinner' THEN 3 
         END,
         f.name;

-- 추가 테스트용 쿼리들

-- 1. 모든 음식과 영양정보 조회
SELECT 
    f.id, f.name, f.price,
    nf.calories, nf.protein, nf.carbohydrate, nf.fat
FROM t_food f
JOIN t_nutrition_fact nf ON f.nutrition_fact_id = nf.id
ORDER BY f.id;

-- 2. 가격대별 음식 개수 확인
SELECT 
    f.price,
    COUNT(*) as food_count
FROM t_food f
GROUP BY f.price
ORDER BY f.price;

-- 3. 특정 날짜의 식단 선택지 조회 (예: 월요일)
SELECT 
    fcs.food_time,
    f.name as food_name,
    f.price,
    f.main1, f.main2, f.side1, f.side2, f.side3
FROM t_daily_food_plan dfp
JOIN t_daily_food_plan_food_choice_set dfp_fcs ON dfp.id = dfp_fcs.daily_food_plan_id
JOIN t_food_choice_set fcs ON dfp_fcs.food_choice_set_id = fcs.id
JOIN t_food_choice_set_food fcs_f ON fcs.id = fcs_f.food_choice_set_id
JOIN t_food f ON fcs_f.food_id = f.id
WHERE dfp.day = '2025-06-23'  -- 월요일
ORDER BY 
    CASE fcs.food_time 
        WHEN 'Breakfast' THEN 1 
        WHEN 'Lunch' THEN 2 
        WHEN 'Dinner' THEN 3 
    END,
    f.name;

-- 4. ReviewSummary 데이터 확인
SELECT 
    rs.food_id, f.name, rs.average_rating, rs.total_reviews
FROM t_review_summary rs
JOIN t_food f ON rs.food_id = f.id
ORDER BY rs.food_id;


INSERT INTO nimn.t_user (`detail_address`, `email`, `name`, `password`, `phone_number`, `road_address`, `role`) VALUES
        ('101호', 'user1@example.com', '홍길동', '$2a$10$KrSz2Mv8PSBxTGDV8CzWneqNFmFTg/6xFOTbCOb5Ub1851onabPaK', '01012345678', '서울 강남구 테헤란로', 'ROLE_USER'),
        ('202호', 'user2@example.com', '김철수', '$2a$10$qL7V0LG63jbkPrwE5csh5uot3PNg0JY3U7hYNhFls1sf3c/rZ0mty', '01023456789', '서울 서초구 반포대로', 'ROLE_USER'),
        ('303호', 'user3@example.com', '이영희', '$2a$10$mfoFoHXAvnAd/BByUsteweJy4TgA6gk6/5ijcUL8JEhvTvOVqViry', '01034567890', '서울 마포구 월드컵북로', 'ROLE_USER'),
        ('505호', 'user4@example.com', '박지민', '$2a$10$9aEbEwxzIA1wdwJJ4EEccON0GJ5mLdk6.a.bGZ3zTB1NX/zyQLoYa', '01056789012', '서울 송파구 송파대로', 'ROLE_USER'),
        ('606호', 'user5@example.com', '최유리', '$2a$10$B7M3f7pv3IGeuA4I4cElmumUTl8pYdNHNyoyu72bDno5hac4nefRy', '01067890123', '서울 동작구 흑석로', 'ROLE_USER'),
        ('404호', 'admin@example.com', '관리자', '$2a$10$/Ut06wjRs2DUww4WhWGxu.LuqHg8VG7zO9nEIhkAxJBW8kB6tLcFe', '01045678901', '서울 종로구 사직로', 'ROLE_ADMIN');

SELECT * FROM nimn.t_user;

-- 주의사항 및 실행 전 확인사항:
-- 1. 실제 테이블 구조와 필드명이 일치하는지 확인
-- 2. AUTO_INCREMENT로 생성되는 ID값들이 예상과 다를 수 있음
-- 3. JPA가 생성하는 중간테이블명과 필드명 확인 필요
-- 4. 외래키 제약조건 확인 필요

-- 만약 외래키 필드명이 다르다면 아래 쿼리로 확인:
-- SHOW CREATE TABLE t_daily_food_plan;
-- SHOW CREATE TABLE t_daily_food_plan_food_choice_set;
-- SHOW CREATE TABLE t_food_choice_set_food;