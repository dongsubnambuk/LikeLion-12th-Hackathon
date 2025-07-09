import React, { useState, useEffect } from "react";
import '../CSS/Admin.css';
import { useNavigate } from 'react-router-dom';
import logo from '../images/logo.png';

function Admin() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [dietSubTab, setDietSubTab] = useState('create');
  const [dietDetailSubTab, setDietDetailSubTab] = useState('list');
  const [selectedDiet, setSelectedDiet] = useState(null);
  const [orders, setOrders] = useState([]);
  const [dietList, setDietList] = useState([]);
  const [dietListWithImages, setDietListWithImages] = useState([]); // 이미지가 포함된 식단 목록
  const [currentPage, setCurrentPage] = useState(1);
  const [priceFilter, setPriceFilter] = useState('all');
  const [price, setPrice] = useState('');
  const [loading, setLoading] = useState(false);
  const [imagesLoading, setImagesLoading] = useState(false); // 이미지 로딩 상태
  const [item, setItem] = useState({
    id: "",
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
  const [adminInfo, setAdminInfo] = useState({
    name: "관리자",
    role: "관리자1"
  });
  const navigate = useNavigate();

  // 브라우저 히스토리 관리
  useEffect(() => {
    const handlePopState = (event) => {
      if (event.state) {
        const { tab, dietSubTab, dietDetailSubTab, selectedDiet } = event.state;
        setActiveTab(tab || 'dashboard');
        setDietSubTab(dietSubTab || 'create');
        setDietDetailSubTab(dietDetailSubTab || 'list');
        setSelectedDiet(selectedDiet || null);
      }
    };

    window.addEventListener('popstate', handlePopState);

    // 초기 상태를 히스토리에 추가
    window.history.replaceState({
      tab: activeTab,
      dietSubTab: dietSubTab,
      dietDetailSubTab: dietDetailSubTab,
      selectedDiet: selectedDiet
    }, '', window.location.pathname);

    // 컴포넌트 언마운트 시 히스토리 정리
    return () => {
      window.removeEventListener('popstate', handlePopState);
      
      // 히스토리 초기화 - 현재 페이지 상태만 남기고 모든 Admin 관련 히스토리 제거
      if (window.history.state && (window.history.state.tab || window.history.state.dietSubTab)) {
        window.history.replaceState(null, '', window.location.pathname);
      }
    };
  }, []);

  // 탭 변경 시 히스토리 업데이트
  const updateHistory = (newTab, newDietSubTab = dietSubTab, newDietDetailSubTab = dietDetailSubTab, newSelectedDiet = selectedDiet) => {
    const state = {
      tab: newTab,
      dietSubTab: newDietSubTab,
      dietDetailSubTab: newDietDetailSubTab,
      selectedDiet: newSelectedDiet
    };
    
    window.history.pushState(state, '', window.location.pathname);
  };

  // 탭 변경 핸들러들
  const handleTabChange = (tab) => {
    setActiveTab(tab);
    if (tab === 'diet' && dietListWithImages.length === 0) {
      loadDietListWithImages();
    }
    updateHistory(tab, dietSubTab, dietDetailSubTab, selectedDiet);
  };

  const handleDietSubTabChange = (subTab) => {
    setDietSubTab(subTab);
    setDietDetailSubTab('list');
    if (subTab === 'list' && dietListWithImages.length === 0) {
      loadDietListWithImages();
    }
    updateHistory(activeTab, subTab, 'list', selectedDiet);
  };

  // 로그아웃 처리
  const handleLogout = async () => {
    // 로그아웃 전 히스토리 정리
    if (window.history.state && (window.history.state.tab || window.history.state.dietSubTab)) {
      window.history.replaceState(null, '', window.location.pathname);
    }
    
    try {
      const response = await fetch('https://nimn.store/api/users/logout', {
        method: 'POST',
        credentials: 'include'
      });

      if (response.ok) {
        localStorage.removeItem("token");
        localStorage.removeItem("email");
        navigate("/");
      } else {
        localStorage.removeItem("token");
        localStorage.removeItem("email");
        navigate("/");
      }
    } catch (error) {
      localStorage.removeItem("token");
      localStorage.removeItem("email");
      navigate("/");
    }
  };

  // 가격 선택 핸들러
  const handlePriceChange = (e) => {
    const selectedPrice = e.target.value;
    setPrice(selectedPrice);
  };

  // 관리자 정보 조회
  const fetchAdminInfo = async () => {
    try {
      const response = await fetch('https://nimn.store/api/users', {
        method: 'GET',
        credentials: 'include'
      });

      if (response.ok) {
        const result = await response.json();
        setAdminInfo({
          name: result.name || "관리자",
          role: "관리자"
        });
      }
    } catch (error) {
    }
  };

  // 주문 목록 조회
  const fetchOrders = async () => {
    try {
      const response = await fetch('https://nimn.store/api/payment/all', {
        method: 'GET',
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('주문 목록 조회에 실패했습니다.');
      }

      const result = await response.json();
      
      const processedOrders = (result || []).map(order => ({
        id: order.id,
        paymentDate: order.createdAt ? order.createdAt.split('T')[0] : '',
        userName: order.purchaser || '알 수 없음',
        amount: order.totalPrice ? `${order.totalPrice}원` : '0원',
        weeklyDietId: order.weeklyDietId || '',
        uid: order.uid || ''
      }));
      
      setOrders(processedOrders);
    } catch (error) {
      setOrders([]);
    }
  };

  // 이미지 다운로드 함수
  const fetchImage = async (imagePath) => {
    if (!imagePath) return logo;
    
    try {
      const response = await fetch(`https://nimn.store${imagePath}`, {
        method: 'GET',
        credentials: 'include'
      });
      
      if (!response.ok) {
        throw new Error('이미지 다운로드에 실패했습니다.');
      }
      
      const blob = await response.blob();
      const imageUrl = URL.createObjectURL(blob);
      return imageUrl;
    } catch (error) {
      return logo;
    }
  };

  // 식단 목록 조회 (이미지 없이)
  const fetchDietList = async () => {
    try {
      const response = await fetch('https://nimn.store/api/foods/plans', {
        method: 'GET',
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('식단 목록 조회에 실패했습니다.');
      }

      const result = await response.json();
      setDietList(result || []);
    } catch (error) {
      setDietList([]);
    }
  };

  // 식단 목록 조회 (이미지 포함) - 식단 탭에서만 호출
  const loadDietListWithImages = async () => {
    if (dietList.length === 0) {
      await fetchDietList();
    }
    
    setImagesLoading(true);
    
    try {
      const processedDietList = await Promise.all(
        dietList.map(async (diet) => {
          const imageUrl = await fetchImage(diet.image);
          return {
            ...diet,
            image: imageUrl
          };
        })
      );
      
      setDietListWithImages(processedDietList);
    } catch (error) {
      setDietListWithImages(dietList.map(diet => ({ ...diet, image: logo })));
    } finally {
      setImagesLoading(false);
    }
  };

  // 식단 생성
  const handleButtonClick = async () => {
    if (!price) {
      alert('가격을 선택해주세요.');
      return;
    }

    setLoading(true);
    
    try {
      const response = await fetch(`https://nimn.store/api/foods/food?price=${price}`, {
        method: 'POST',
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('식단 생성에 실패했습니다.');
      }

      const result = await response.json();
      
      const imageUrl = await fetchImage(result.image);
      
      const newItem = {
        id: result.id || "",
        name: result.name || "",
        main1: result.main1 || "",
        main2: result.main2 || "",
        price: result.price || `${price}`,
        side1: result.side1 || "",
        side2: result.side2 || "",
        side3: result.side3 || "",
        calories: result.calories || "",
        carbohydrate: result.carbohydrate || "",
        protein: result.protein || "",
        fat: result.fat || "",
        sugar: result.sugar || "",
        sodium: result.sodium || "",
        image: imageUrl || logo
      };
      
      setItem(newItem);
      setItemVisible(true);
      
      // 기존 목록 업데이트
      await fetchDietList();
      setDietListWithImages(prev => [newItem, ...prev]);
      
      setPrice('');
      alert('식단이 생성되었습니다!');
    } catch (error) {
      alert('식단 생성 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  // 식단 클릭 핸들러
  const handleDietClick = async (diet) => {
    let dietWithImage = diet;
    
    // 이미지가 없는 경우 또는 기본 logo인 경우 다운로드
    if (!diet.image || diet.image === logo || typeof diet.image === 'string' && diet.image.includes('/api/')) {
      const imageUrl = await fetchImage(diet.image);
      dietWithImage = { ...diet, image: imageUrl };
    }
    
    setSelectedDiet(dietWithImage);
    
    if (activeTab === 'dashboard') {
      setActiveTab('diet');
      setDietSubTab('list');
      setDietDetailSubTab('detail');
      
      // 대시보드에서 클릭한 경우 이미지가 포함된 식단 목록도 업데이트
      if (dietListWithImages.length === 0) {
        loadDietListWithImages();
      } else {
        // 기존 목록에서 해당 식단의 이미지만 업데이트
        setDietListWithImages(prev => prev.map(item => 
          item.id === diet.id ? dietWithImage : item
        ));
      }
      
      updateHistory('diet', 'list', 'detail', dietWithImage);
    } else {
      setDietDetailSubTab('detail');
      updateHistory(activeTab, dietSubTab, 'detail', dietWithImage);
    }
  };

  // 더보기 버튼 핸들러들
  const handleViewMoreDiets = () => {
    setActiveTab('diet');
    setDietSubTab('list');
    setDietDetailSubTab('list');
    if (dietListWithImages.length === 0) {
      loadDietListWithImages();
    }
    updateHistory('diet', 'list', 'list', selectedDiet);
  };

  const handleViewMoreOrders = () => {
    setActiveTab('orders');
    updateHistory('orders', dietSubTab, dietDetailSubTab, selectedDiet);
  };

  // 뒤로가기 핸들러
  const handleBackToList = () => {
    setDietDetailSubTab('list');
    updateHistory(activeTab, dietSubTab, 'list', null);
  };

  // 가격 필터링
  const getFilteredDietList = () => {
    const listToFilter = dietListWithImages.length > 0 ? dietListWithImages : dietList;
    if (priceFilter === 'all') {
      return listToFilter;
    }
    return listToFilter.filter(diet => diet.price === parseInt(priceFilter));
  };

  const filteredDietList = getFilteredDietList();

  // 컴포넌트 마운트 시 데이터 로드
  useEffect(() => {
    fetchAdminInfo();
    fetchDietList(); // 이미지 없는 기본 목록만 로드
    fetchOrders();
  }, []);

  // 페이지네이션 계산
  const ordersPerPage = 10;
  const totalOrderPages = Math.ceil(orders.length / ordersPerPage);
  const startOrderIndex = (currentPage - 1) * ordersPerPage;
  const currentOrders = orders.slice(startOrderIndex, startOrderIndex + ordersPerPage);

  return (
    <div className="admin_layout">
      {/* 사이드바 */}
      <div className="admin_sidebar">
        <div className="admin_profile">
          <div className="admin_profile_icon">👤</div>
          <div className="admin_profile_info">
            <div className="admin_profile_name">{adminInfo.name}</div>
            <div className="admin_profile_role">{adminInfo.role}</div>
          </div>
        </div>
        
        <nav className="admin_nav">
          <button 
            className={`admin_nav_button ${activeTab === 'dashboard' ? 'admin_nav_active' : ''}`}
            onClick={() => handleTabChange('dashboard')}
          >
            <span className="admin_nav_icon"></span>
            <span className="admin_nav_text">대시보드</span>
          </button>
          <button 
            className={`admin_nav_button ${activeTab === 'diet' ? 'admin_nav_active' : ''}`}
            onClick={() => handleTabChange('diet')}
          >
            <span className="admin_nav_icon"></span>
            <span className="admin_nav_text">식단 관리</span>
          </button>
          <button 
            className={`admin_nav_button ${activeTab === 'orders' ? 'admin_nav_active' : ''}`}
            onClick={() => handleTabChange('orders')}
          >
            <span className="admin_nav_icon"></span>
            <span className="admin_nav_text">주문 관리</span>
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
              {activeTab === 'diet' && '식단 관리'}
              {activeTab === 'orders' && '주문 관리'}
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
                    <h3 className="admin_card_title">생성된 식단</h3>

                  </div>
                  <div className="admin_card_content">
                    <div className="admin_card_number">{dietList.length}</div>
                    <div className="admin_card_label">개</div>
                  </div>
                </div>

                <div className="admin_dashboard_card">
                  <div className="admin_card_header">
                    <h3 className="admin_card_title">총 주문</h3>
            
                  </div>
                  <div className="admin_card_content">
                    <div className="admin_card_number">{orders.length}</div>
                    <div className="admin_card_label">건</div>
                  </div>
                </div>
              </div>

              <div className="admin_dashboard_lists">
                <div className="admin_list_container">
                  <div className="admin_list_header">
                    <h3 className="admin_list_title">전체 식단</h3>
                    <button className="admin_more_btn" onClick={handleViewMoreDiets}>
                      목록보기
                    </button>
                  </div>
                  <div className="admin_dashboard_diet_list">
                    {dietList.slice(0, 5).map((diet) => (
                      <div 
                        key={diet.id} 
                        className="admin_dashboard_diet_item"
                        onClick={() => handleDietClick(diet)}
                      >
                        <div className="admin_dashboard_diet_info">
                          <div className="admin_dashboard_diet_name">{diet.name}</div>
                          <div className="admin_dashboard_diet_detail">{`${diet.price}원`} • {diet.calories}</div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="admin_list_container">
                  <div className="admin_list_header">
                    <h3 className="admin_list_title">최근 주문 내역</h3>
                    <button className="admin_more_btn" onClick={handleViewMoreOrders}>
                      목록보기
                    </button>
                  </div>
                  <div className="admin_dashboard_order_list">
                    {orders.slice(0, 5).map((order) => (
                      <div key={order.id} className="admin_dashboard_order_item">
                        <div className="admin_dashboard_order_info">
                          <div className="admin_dashboard_order_name">{order.userName}</div>
                          <div className="admin_dashboard_order_detail"> {order.amount.replace(/(\d+)/, (match) => parseInt(match).toLocaleString())}</div>
                        </div>
                        <div className="admin_dashboard_order_date">{order.paymentDate}</div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* 주문 관리 */}
          {activeTab === 'orders' && (
            <div className="admin_orders_section">
              <div className="admin_section_header">
                <h2 className="admin_section_title">주문 관리</h2>
              </div>
              
              <table className="admin_orders_table">
                <thead className="admin_table_head">
                  <tr className="admin_table_row">
                    <th className="admin_table_header">결제 ID</th>
                    <th className="admin_table_header">결제 날짜</th>
                    <th className="admin_table_header">구매자</th>
                    <th className="admin_table_header">결제 금액</th>
                    <th className="admin_table_header">주문 ID</th>
                  </tr>
                </thead>
                <tbody className="admin_table_body">
                  {currentOrders.map((order) => (
                    <tr key={order.id} className="admin_table_row">
                      <td className="admin_table_cell">{order.id}</td>
                      <td className="admin_table_cell">{order.paymentDate}</td>
                      <td className="admin_table_cell">{order.userName}</td>
                      <td className="admin_table_cell">
                        {order.amount.replace(/(\d+)/, (match) => parseInt(match).toLocaleString())}
                      </td>
                      <td className="admin_table_cell">{order.uid}</td>
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
                  onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalOrderPages))}
                  disabled={currentPage === totalOrderPages}
                >
                  다음
                </button>
              </div>
            </div>
          )}

          {/* 식단 관리 */}
          {activeTab === 'diet' && (
            <div className="admin_diet_section">
              {/* 서브탭 */}
              <div className="admin_diet_subtabs">
                <button 
                  className={`admin_subtab_btn ${dietSubTab === 'create' ? 'admin_subtab_active' : ''}`}
                  onClick={() => handleDietSubTabChange('create')}
                >
                  식단 생성하기
                </button>
                <button 
                  className={`admin_subtab_btn ${dietSubTab === 'list' ? 'admin_subtab_active' : ''}`}
                  onClick={() => handleDietSubTabChange('list')}
                >
                  생성된 식단 ({dietList.length})
                </button>
              </div>

              {/* 식단 생성 */}
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
                          <h4 className="admin_result_title">생성된 식단</h4>
                          <span className="admin_new_badge">NEW</span>
                        </div>
                        <div className="admin_diet_card">
                          <div className="admin_diet_content">
                            <div className="admin_diet_left">
                              <div className="admin_diet_image_container">
                                <img src={item.image} alt="food" className="admin_diet_image" />
                              </div>
                              <div className="admin_diet_info_container">
                                <h3 className="admin_diet_name">{item.name}</h3>
                                <div className="admin_diet_info_summary">
                                  <div className="admin_diet_info">
                                    <span className="admin_info_text">{item.calories}</span>
                                  </div>
                                  <div className="admin_diet_info">
                                    <span className="admin_info_text">{item.price}</span>
                                  </div>
                                </div>
                              </div>
                            </div>
                            
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
                            </div>
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              )}

              {/* 식단 목록 */}
              {dietSubTab === 'list' && (
                <>
                  {dietDetailSubTab === 'list' && (
                    <div className="admin_diet_list_container">
                      <div className="admin_diet_list_header">
                        <h3 className="admin_list_title">생성된 식단 목록</h3>
                        <div className="admin_filter_section">
                          <select 
                            value={priceFilter} 
                            onChange={(e) => setPriceFilter(e.target.value)}
                            className="admin_price_filter"
                          >
                            <option value="all">전체 가격</option>
                            <option value="4000">4000원</option>
                            <option value="5500">5500원</option>
                            <option value="7000">7000원</option>
                          </select>
                          <span className="admin_list_count">총 {filteredDietList.length}개</span>
                        </div>
                      </div>
                      
                      {imagesLoading && (
                        <div className="admin_spinner_container">
                          <div className="admin_spinner"></div>
                          <p className="admin_loading_text">이미지를 불러오는 중...</p>
                        </div>
                      )}
                      
                      <div className="admin_diet_list_content">
                        {!imagesLoading && filteredDietList.length > 0 ? (
                          <div className="admin_diet_list">
                            {filteredDietList.map((diet) => (
                              <div 
                                key={diet.id} 
                                className="admin_diet_list_item"
                                onClick={() => handleDietClick(diet)}
                              >
                                <div className="admin_diet_list_image">
                                  <img src={diet.image || logo} alt={diet.name} />
                                </div>
                                <div className="admin_diet_list_info">
                                  <div className="admin_diet_list_name">{diet.name}</div>
                                  <div className="admin_diet_list_details">
                                    <span>{diet.price}원</span>
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
                                  <div className="admin_diet_list_actions">
                                    <button className="admin_diet_action_btn admin_view_btn">보기</button>
                                  </div>
                                </div>
                              </div>
                            ))}
                          </div>
                        ) : !imagesLoading && filteredDietList.length === 0 ? (
                          <div className="admin_empty_state">
                            <div className="admin_empty_text">해당 가격대의 식단이 없습니다.</div>
                            <div className="admin_empty_subtext">
                              <button 
                                className="admin_empty_link"
                                onClick={() => handleDietSubTabChange('create')}
                              >
                                새로운 식단 생성하기
                              </button>
                            </div>
                          </div>
                        ) : null}
                      </div>
                    </div>
                  )}

                  {/* 식단 상세 보기 */}
                  {dietDetailSubTab === 'detail' && selectedDiet && (
                    <div className="admin_diet_detail_container">
                      <div className="admin_diet_detail_header">
                        <button 
                          className="admin_back_btn"
                          onClick={handleBackToList}
                        >
                          목록으로 돌아가기
                        </button>
                        <h3 className="admin_detail_main_title">{selectedDiet.name}</h3>
                      </div>
                      
                      <div className="admin_diet_card">
                        <div className="admin_diet_content">
                          <div className="admin_diet_left">
                            <div className="admin_diet_image_container">
                              <img src={selectedDiet.image} alt="food" className="admin_diet_image" />
                            </div>
                            <div className="admin_diet_info_container">
                              <h3 className="admin_diet_name">{selectedDiet.name}</h3>
                              <div className="admin_diet_info_summary">
                                <div className="admin_diet_info">
                                  <span className="admin_info_text">{selectedDiet.calories}</span>
                                </div>
                                <div className="admin_diet_info">
                                  <span className="admin_info_text">{selectedDiet.price}원</span>
                                </div>
                              </div>
                            </div>
                          </div>
                          
                          <div className="admin_menu_section">
                            <h4 className="admin_detail_title">메뉴 구성</h4>
                            <div className="admin_menu_grid">
                              <div className="admin_menu_item">
                                <span className="admin_menu_label">메인1:</span>
                                <span className="admin_menu_value">{selectedDiet.main1}</span>
                              </div>
                              <div className="admin_menu_item">
                                <span className="admin_menu_label">메인2:</span>
                                <span className="admin_menu_value">{selectedDiet.main2}</span>
                              </div>
                              <div className="admin_menu_item">
                                <span className="admin_menu_label">사이드1:</span>
                                <span className="admin_menu_value">{selectedDiet.side1}</span>
                              </div>
                              <div className="admin_menu_item">
                                <span className="admin_menu_label">사이드2:</span>
                                <span className="admin_menu_value">{selectedDiet.side2}</span>
                              </div>
                              <div className="admin_menu_item">
                                <span className="admin_menu_label">사이드3:</span>
                                <span className="admin_menu_value">{selectedDiet.side3}</span>
                              </div>
                            </div>
                          </div>
                          
                          <div className="admin_nutrition_section">
                            <h4 className="admin_detail_title">영양성분</h4>
                            <div className="admin_nutrition_grid">
                              <div className="admin_nutrition_item">
                                <span className="admin_nutrition_label">탄수화물</span>
                                <span className="admin_nutrition_value">{selectedDiet.carbohydrate}</span>
                              </div>
                              <div className="admin_nutrition_item">
                                <span className="admin_nutrition_label">단백질</span>
                                <span className="admin_nutrition_value">{selectedDiet.protein}</span>
                              </div>
                              <div className="admin_nutrition_item">
                                <span className="admin_nutrition_label">지방</span>
                                <span className="admin_nutrition_value">{selectedDiet.fat}</span>
                              </div>
                              <div className="admin_nutrition_item">
                                <span className="admin_nutrition_label">당류</span>
                                <span className="admin_nutrition_value">{selectedDiet.sugar}</span>
                              </div>
                              <div className="admin_nutrition_item">
                                <span className="admin_nutrition_label">나트륨</span>
                                <span className="admin_nutrition_value">{selectedDiet.sodium}</span>
                              </div>
                            </div>
                          </div>

                          <div className="admin_diet_extra">
                            <div className="admin_diet_summary">
                              <h4 className="admin_summary_title">식단 요약</h4>
                              <div className="admin_summary_stats">
                                <div className="admin_summary_item">
                                  <span className="admin_summary_label">가격대</span>
                                  <span className="admin_summary_value">{`${selectedDiet.price}원`}</span>
                                </div>
                                <div className="admin_summary_item">
                                  <span className="admin_summary_label">칼로리</span>
                                  <span className="admin_summary_value">{selectedDiet.calories}</span>
                                </div>
                                <div className="admin_summary_item">
                                  <span className="admin_summary_label">메뉴 구성</span>
                                  <span className="admin_summary_value">5개</span>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  )}
                </>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Admin;