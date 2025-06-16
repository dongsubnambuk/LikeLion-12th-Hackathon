import React, { useState, useEffect } from "react";
import '../CSS/Admin.css';
import { useNavigate } from 'react-router-dom';
import foodimage from'../images/mainCardImg1.jpeg';

function Admin() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [dietSubTab, setDietSubTab] = useState('create'); // 식단 관리 내 서브탭
  const [users, setUsers] = useState([]);
  const [dietList, setDietList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [price, setPrice] = useState('');
  const [loading, setLoading] = useState(false);
  const [item, setItem] = useState({
    name: "",
    main1: "",
    main2: "",
    price: "",
    side1: "",
    side2: "",
    side3: "",
    calories: "",
    carbohydrate: "",
    protein: "",
    fat: "",
    sugar: "",
    sodium: "",
    image: ""
  });
  const [itemVisible, setItemVisible] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    navigate("/");
  };

  const handlePriceChange = (e) => {
    const selectedPrice = e.target.value;
    setPrice(selectedPrice);
  };

  // 식단 생성 fetch
  const handleButtonClick = async () => {
    if (!price) {
      alert('가격을 선택해주세요.');
      return;
    }

    const token = localStorage.getItem("token");
    setLoading(true);
    
    // 예시 데이터를 사용한 시뮬레이션
    try {
      // 실제 API 호출 대신 예시 데이터 사용
      setTimeout(() => {
        const exampleMenus = {
          "4000": {
            name: "건강한 가정식",
            main1: "제육볶음",
            main2: "달걀찜",
            side1: "배추김치",
            side2: "시금치나물",
            side3: "미소된장국",
            calories: "520kcal",
            carbohydrate: "65g",
            protein: "22g",
            fat: "16g",
            sugar: "8g",
            sodium: "980mg",
            image: foodimage
          },
          "5500": {
            name: "균형 영양 도시락",
            main1: "닭가슴살 구이",
            main2: "연근조림",
            side1: "현미밥",
            side2: "브로콜리 무침",
            side3: "맑은 콩나물국",
            calories: "465kcal",
            carbohydrate: "58g",
            protein: "28g",
            fat: "12g",
            sugar: "6g",
            sodium: "750mg",
            image: foodimage          },
          "7000": {
            name: "프리미엄 한식 세트",
            main1: "갈비찜",
            main2: "생선구이",
            side1: "나물 3종",
            side2: "김치찌개",
            side3: "깍두기",
            calories: "680kcal",
            carbohydrate: "72g",
            protein: "35g",
            fat: "22g",
            sugar: "12g",
            sodium: "1200mg",
            image: foodimage          }
        };

        const selectedMenu = exampleMenus[price];
        setItem({
          ...selectedMenu,
          price: `${price}원`
        });
        setItemVisible(true);
        setLoading(false);
      }, 2000); // 2초 로딩 시뮬레이션

      // 실제 API 호출 코드 (주석 처리)
      /*
      const response = await fetch(`http://3.37.64.39:8000/api/meal/food-menu?price=${price}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        },
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setItem({
        name: data.name,
        main1: data.main1,
        main2: data.main2,
        price: `${price}원`,
        side1: data.side1,
        side2: data.side2,
        side3: data.side3,
        calories: data.calories,
        carbohydrate: data.carbohydrate,
        protein: data.protein,
        fat: data.fat,
        sugar: data.sugar,
        sodium: data.sodium,
        image: data.image
      });
      setItemVisible(true);
      setLoading(false);
      */
    } catch (error) {
      console.error('Fetch error:', error);
      setLoading(false);
      alert('식단 생성 중 오류가 발생했습니다.');
    }
  };

  // 식단 적용 버튼
  const handleApplyDiet = () => {
    const newDiet = {
      id: dietList.length + 1,
      ...item,
      createdAt: new Date().toLocaleDateString()
    };
    setDietList([newDiet, ...dietList]);
    setItemVisible(false);
    setPrice('');
    alert('식단이 적용되었습니다!');
  };

  // 사용자 정보 가져오기 (예시 데이터)
  useEffect(() => {
    const mockUsers = [
      {
        id: 1,
        name: "테스트1",
        email: "test1@naver.com",
        phone: "01012345678",
        postcode: "12345",
        address: "도봉구 주소",
        detail: "상세 주소"
      },
      {
        id: 2,
        name: "테스트",
        email: "test14@naver.com",
        phone: "010-2222-3333",
        postcode: "13480",
        address: "경기 성남시 분당구 대왕판교로 477",
        detail: "111"
      },
      {
        id: 3,
        name: "테스트2",
        email: "test3@naver.com",
        phone: "01012345678",
        postcode: "12345",
        address: "도봉구 주소",
        detail: "상세 주소"
      }
    ];
    setUsers(mockUsers);

    // 예시 식단 데이터
    const mockDietList = [
      {
        id: 1,
        name: "균형잡힌 한식 도시락",
        main1: "불고기",
        main2: "계란말이",
        side1: "김치",
        side2: "콩나물무침",
        side3: "미역국",
        price: "7000원",
        calories: "650kcal",
        carbohydrate: "85g",
        protein: "28g",
        fat: "18g",
        sugar: "12g",
        sodium: "1200mg",
        image: foodimage,   
        createdAt: "2025-06-16"
      },
      {
        id: 2,
        name: "저칼로리 샐러드 세트",
        main1: "그릴드 치킨",
        main2: "방울토마토",
        side1: "믹스 샐러드",
        side2: "견과류",
        side3: "발사믹 드레싱",
        price: "5500원",
        calories: "420kcal",
        carbohydrate: "25g",
        protein: "35g",
        fat: "15g",
        sugar: "8g",
        sodium: "680mg",
        image: foodimage,
        createdAt: "2025-06-15"
      },
      {
        id: 3,
        name: "단백질 파워 도시락",
        main1: "연어구이",
        main2: "두부스테이크",
        side1: "현미밥",
        side2: "브로콜리",
        side3: "새싹채소",
        price: "7000원",
        calories: "580kcal",
        carbohydrate: "45g",
        protein: "42g",
        fat: "20g",
        sugar: "6g",
        sodium: "850mg",
        image: foodimage,
        createdAt: "2025-06-14"
      },
      {
        id: 4,
        name: "채식주의자 건강식",
        main1: "두부버거",
        main2: "퀴노아",
        side1: "아보카도",
        side2: "당근스틱",
        side3: "허브소스",
        price: "5500원",
        calories: "480kcal",
        carbohydrate: "55g",
        protein: "18g",
        fat: "22g",
        sugar: "10g",
        sodium: "590mg",
        image: foodimage,
        createdAt: "2025-06-13"
      },
      {
        id: 5,
        name: "전통 한정식",
        main1: "갈비찜",
        main2: "생선구이",
        side1: "나물 3종",
        side2: "된장찌개",
        side3: "깍두기",
        price: "7000원",
        calories: "720kcal",
        carbohydrate: "78g",
        protein: "32g",
        fat: "25g",
        sugar: "15g",
        sodium: "1350mg",
        image: foodimage,
        createdAt: "2025-06-12"
      },
      {
        id: 6,
        name: "지중해식 다이어트",
        main1: "그릴드 생선",
        main2: "올리브오일 파스타",
        side1: "그릭 샐러드",
        side2: "체리토마토",
        side3: "페타치즈",
        price: "6000원",
        calories: "520kcal",
        carbohydrate: "48g",
        protein: "28g",
        fat: "24g",
        sugar: "9g",
        sodium: "750mg",
        image: foodimage,
        createdAt: "2025-06-11"
      }
    ];
    setDietList(mockDietList);
  }, []);

  const usersPerPage = 5;
  const totalPages = Math.ceil(users.length / usersPerPage);
  const startIndex = (currentPage - 1) * usersPerPage;
  const currentUsers = users.slice(startIndex, startIndex + usersPerPage);

  return (
    <div className="admin_layout">
      {/* 사이드바 */}
      <div className="admin_sidebar">
        <div className="admin_profile">
          <div className="admin_profile_icon">👤</div>
          <div className="admin_profile_info">
            <div className="admin_profile_name">관리자</div>
            <div className="admin_profile_role">관리자1</div>
          </div>
        </div>
        
        <nav className="admin_nav">
          <button 
            className={`admin_nav_button ${activeTab === 'dashboard' ? 'admin_nav_active' : ''}`}
            onClick={() => setActiveTab('dashboard')}
          >
            <span className="admin_nav_icon">📊</span>
            <span className="admin_nav_text">대시보드</span>
          </button>
          <button 
            className={`admin_nav_button ${activeTab === 'users' ? 'admin_nav_active' : ''}`}
            onClick={() => setActiveTab('users')}
          >
            <span className="admin_nav_icon">👥</span>
            <span className="admin_nav_text">유저 관리</span>
          </button>
          <button 
            className={`admin_nav_button ${activeTab === 'diet' ? 'admin_nav_active' : ''}`}
            onClick={() => setActiveTab('diet')}
          >
            <span className="admin_nav_icon">🍽️</span>
            <span className="admin_nav_text">식단 관리</span>
          </button>
        </nav>
      </div>

      {/* 메인 컨텐츠 */}
      <div className="admin_main">
        <header className="admin_header">
          <div className="admin_header_left">
            <h1 className="admin_title">우리동네영양사</h1>
            <div className="admin_breadcrumb">
              {activeTab === 'dashboard' && '대시보드'}
              {activeTab === 'users' && '유저 정보'}
              {activeTab === 'diet' && '식단 관리'}
            </div>
          </div>
          <div className="admin_header_right">
            <span className="admin_logout_btn" onClick={handleLogout}>로그아웃</span>
          </div>
        </header>

        <div className="admin_content">
          {/* 대시보드 */}
          {activeTab === 'dashboard' && (
            <div className="admin_dashboard_section">
              <div className="admin_section_header">
                <h2 className="admin_section_title">대시보드</h2>
              </div>
              
              <div className="admin_dashboard_grid">
                <div className="admin_dashboard_card">
                  <div className="admin_card_header">
                    <h3 className="admin_card_title">전체 유저</h3>
                    <span className="admin_card_icon">👥</span>
                  </div>
                  <div className="admin_card_content">
                    <div className="admin_card_number">{users.length}</div>
                    <div className="admin_card_label">명</div>
                  </div>
                </div>

                <div className="admin_dashboard_card">
                  <div className="admin_card_header">
                    <h3 className="admin_card_title">생성된 식단</h3>
                    <span className="admin_card_icon">🍽️</span>
                  </div>
                  <div className="admin_card_content">
                    <div className="admin_card_number">{dietList.length}</div>
                    <div className="admin_card_label">개</div>
                  </div>
                </div>
              </div>

              <div className="admin_dashboard_lists">
                <div className="admin_list_container">
                  <div className="admin_list_header">
                    <h3 className="admin_list_title">최근 유저</h3>
                  </div>
                  <div className="admin_simple_list">
                    {users.slice(0, 5).map((user) => (
                      <div key={user.id} className="admin_simple_item">
                        <div className="admin_item_info">
                          <div className="admin_item_name">{user.name}</div>
                          <div className="admin_item_email">{user.email}</div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="admin_list_container">
                  <div className="admin_list_header">
                    <h3 className="admin_list_title">최근 생성 식단</h3>
                  </div>
                  <div className="admin_simple_list">
                    {dietList.slice(0, 5).map((diet) => (
                      <div key={diet.id} className="admin_simple_item">
                        <div className="admin_item_info">
                          <div className="admin_item_name">{diet.name}</div>
                          <div className="admin_item_detail">{diet.price} • {diet.calories}</div>
                        </div>
                        <div className="admin_item_date">{diet.createdAt}</div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 유저 관리 */}
          {activeTab === 'users' && (
            <div className="admin_users_section">
              <div className="admin_section_header">
                <h2 className="admin_section_title">전체 유저 정보</h2>
              </div>
              
              <table className="admin_users_table">
                <thead className="admin_table_head">
                  <tr className="admin_table_row">
                    <th className="admin_table_header">이름</th>
                    <th className="admin_table_header">이메일</th>
                    <th className="admin_table_header">전화번호</th>
                    <th className="admin_table_header">우편번호</th>
                    <th className="admin_table_header">도로명 주소</th>
                    <th className="admin_table_header">상세 주소</th>
                  </tr>
                </thead>
                <tbody className="admin_table_body">
                  {currentUsers.map((user) => (
                    <tr key={user.id} className="admin_table_row">
                      <td className="admin_table_cell">{user.name}</td>
                      <td className="admin_table_cell">{user.email}</td>
                      <td className="admin_table_cell">{user.phone}</td>
                      <td className="admin_table_cell">{user.postcode}</td>
                      <td className="admin_table_cell">{user.address}</td>
                      <td className="admin_table_cell">{user.detail}</td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <div className="admin_pagination">
                <button 
                  className="admin_pagination_btn"
                  onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
                  disabled={currentPage === 1}
                >
                  이전
                </button>
                <span className="admin_page_number">{currentPage}</span>
                <button 
                  className="admin_pagination_btn"
                  onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
                  disabled={currentPage === totalPages}
                >
                  다음
                </button>
              </div>
            </div>
          )}

          {/* 식단 관리 */}
          {activeTab === 'diet' && (
            <div className="admin_diet_section">
          
              {/* 식단 관리 서브탭 */}
              <div className="admin_diet_subtabs">
                <button 
                  className={`admin_subtab_btn ${dietSubTab === 'create' ? 'admin_subtab_active' : ''}`}
                  onClick={() => setDietSubTab('create')}
                >
                  🍽️ 식단 생성하기
                </button>
                <button 
                  className={`admin_subtab_btn ${dietSubTab === 'list' ? 'admin_subtab_active' : ''}`}
                  onClick={() => setDietSubTab('list')}
                >
                  📋 생성된 식단 ({dietList.length})
                </button>
              </div>

              {/* 식단 생성 탭 */}
              {dietSubTab === 'create' && (
                <div className="admin_diet_create_container">
                  <div className="admin_diet_form">
                    <div className="admin_price_input">
                      <select
                        id="price"
                        value={price}
                        className="admin_price_select"
                        onChange={handlePriceChange}
                      >
                        <option value="">가격을 선택해주세요</option>
                        <option value="4000">4000원 (기본형)</option>
                        <option value="5500">5500원 (균형형)</option>
                        <option value="7000">7000원 (프리미엄)</option>
                      </select>
                      <button onClick={handleButtonClick} className="admin_create_btn" disabled={loading}>
                        {loading ? '생성 중...' : '식단 생성하기'}
                      </button>
                    </div>

                    {loading && (
                      <div className="admin_spinner_container">
                        <div className="admin_spinner"></div>
                        <p className="admin_loading_text">AI가 맞춤 식단을 생성하고 있습니다...</p>
                      </div>
                    )}

                    {itemVisible && !loading && (
                      <div className="admin_diet_result">
                        <div className="admin_result_header">
                          <h4 className="admin_result_title">✨ 생성된 식단</h4>
                          <span className="admin_new_badge">NEW</span>
                        </div>
                        <div className="admin_diet_card">
                          <div className="admin_diet_content">
                            {/* 첫 번째 섹션 - 이미지와 기본 정보 */}
                            <div className="admin_diet_left">
                              <div className="admin_diet_image_container">
                                <img src={item.image} alt="food" className="admin_diet_image" />
                              </div>
                              <div className="admin_diet_info_container">
                                <h3 className="admin_diet_name">{item.name}</h3>
                                <div className="admin_diet_info_summary">
                                  <div className="admin_diet_info">
                                    <span className="admin_info_icon">🔥</span>
                                    <span className="admin_info_text">{item.calories}</span>
                                  </div>
                                  <div className="admin_diet_info">
                                    <span className="admin_info_icon">💰</span>
                                    <span className="admin_info_text">{item.price}</span>
                                  </div>
                                </div>
                              </div>
                            </div>
                            
                            {/* 두 번째 섹션 - 메뉴 구성 */}
                            <div className="admin_menu_section">
                              <h4 className="admin_detail_title">메뉴 구성</h4>
                              <div className="admin_menu_grid">
                                <div className="admin_menu_item">
                                  <span className="admin_menu_label">메인1:</span>
                                  <span className="admin_menu_value">{item.main1}</span>
                                </div>
                                <div className="admin_menu_item">
                                  <span className="admin_menu_label">메인2:</span>
                                  <span className="admin_menu_value">{item.main2}</span>
                                </div>
                                <div className="admin_menu_item">
                                  <span className="admin_menu_label">사이드1:</span>
                                  <span className="admin_menu_value">{item.side1}</span>
                                </div>
                                <div className="admin_menu_item">
                                  <span className="admin_menu_label">사이드2:</span>
                                  <span className="admin_menu_value">{item.side2}</span>
                                </div>
                                <div className="admin_menu_item">
                                  <span className="admin_menu_label">사이드3:</span>
                                  <span className="admin_menu_value">{item.side3}</span>
                                </div>
                              </div>
                            </div>
                            
                            {/* 세 번째 섹션 - 영양성분 */}
                            <div className="admin_nutrition_section">
                              <h4 className="admin_detail_title">영양성분</h4>
                              <div className="admin_nutrition_grid">
                                <div className="admin_nutrition_item">
                                  <span className="admin_nutrition_label">탄수화물</span>
                                  <span className="admin_nutrition_value">{item.carbohydrate}</span>
                                </div>
                                <div className="admin_nutrition_item">
                                  <span className="admin_nutrition_label">단백질</span>
                                  <span className="admin_nutrition_value">{item.protein}</span>
                                </div>
                                <div className="admin_nutrition_item">
                                  <span className="admin_nutrition_label">지방</span>
                                  <span className="admin_nutrition_value">{item.fat}</span>
                                </div>
                                <div className="admin_nutrition_item">
                                  <span className="admin_nutrition_label">당류</span>
                                  <span className="admin_nutrition_value">{item.sugar}</span>
                                </div>
                                <div className="admin_nutrition_item">
                                  <span className="admin_nutrition_label">나트륨</span>
                                  <span className="admin_nutrition_value">{item.sodium}</span>
                                </div>
                              </div>
                            </div>

                            {/* 네 번째 섹션 - 요약 정보와 버튼 */}
                            <div className="admin_diet_extra">
                              <div className="admin_diet_summary">
                                <h4 className="admin_summary_title">식단 요약</h4>
                                <div className="admin_summary_stats">
                                  <div className="admin_summary_item">
                                    <span className="admin_summary_label">가격대</span>
                                    <span className="admin_summary_value">{item.price}</span>
                                  </div>
                                  <div className="admin_summary_item">
                                    <span className="admin_summary_label">칼로리</span>
                                    <span className="admin_summary_value">{item.calories}</span>
                                  </div>
                                  <div className="admin_summary_item">
                                    <span className="admin_summary_label">메인 메뉴</span>
                                    <span className="admin_summary_value">2개</span>
                                  </div>
                                  <div className="admin_summary_item">
                                    <span className="admin_summary_label">사이드</span>
                                    <span className="admin_summary_value">3개</span>
                                  </div>
                                </div>
                              </div>

                              <div className="admin_action_buttons">
                                <button className="admin_apply_btn" onClick={handleApplyDiet}>
                                  ✅ 식단 적용하기
                                </button>
                                <button className="admin_regenerate_btn" onClick={handleButtonClick}>
                                  🔄 다시 생성하기
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              )}

              {/* 생성된 식단 목록 탭 */}
              {dietSubTab === 'list' && (
                <div className="admin_diet_list_container">
                  <div className="admin_diet_list_header">
                    <h3 className="admin_list_title">생성된 식단 목록</h3>
                    <span className="admin_list_count">총 {dietList.length}개</span>
                  </div>
                  <div className="admin_diet_list_content">
                    {dietList.length > 0 ? (
                      <div className="admin_diet_list">
                        {dietList.map((diet) => (
                          <div key={diet.id} className="admin_diet_list_item">
                            <div className="admin_diet_list_image">
                              <img src={diet.image} alt={diet.name} />
                            </div>
                            <div className="admin_diet_list_info">
                              <div className="admin_diet_list_name">{diet.name}</div>
                              <div className="admin_diet_list_details">
                                <span>{diet.price}</span>
                                <span>•</span>
                                <span>{diet.calories}</span>
                                <span>•</span>
                                <span>메인: {diet.main1}, {diet.main2}</span>
                              </div>
                              <div className="admin_diet_list_nutrients">
                                단백질: {diet.protein} | 탄수화물: {diet.carbohydrate} | 지방: {diet.fat}
                              </div>
                            </div>
                            <div className="admin_diet_list_meta">
                              <div className="admin_diet_list_date">{diet.createdAt}</div>
                              <div className="admin_diet_list_actions">
                                <button className="admin_diet_action_btn admin_delete_btn">삭제</button>
                              </div>
                            </div>
                          </div>
                        ))}
                      </div>
                    ) : (
                      <div className="admin_empty_state">
                        <div className="admin_empty_icon">🍽️</div>
                        <div className="admin_empty_text">아직 생성된 식단이 없습니다.</div>
                        <div className="admin_empty_subtext">
                          <button 
                            className="admin_empty_link"
                            onClick={() => setDietSubTab('create')}
                          >
                            새로운 식단 생성하기
                          </button>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Admin;