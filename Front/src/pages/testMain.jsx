import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css'; // Swiper 기본 CSS
import 'swiper/css/navigation'; // Navigation 모듈의 CSS
import 'swiper/css/pagination'; // Pagination 모듈의 CSS
import '../CSS/MainPage.css';
import '../components/Meals';
import { Spin } from 'antd';
import { Navigation, Pagination, Autoplay } from 'swiper/modules'; // 모듈을 swiper/modules에서 가져오기
import Meals from '../components/Meals';
import Header from '../components/Header';
import BottomNav from '../components/BottomNav';
import logo from '../images/logo.png';
import cat from '../images/cat.png';
import food from '../images/food.png';
import { useParams } from 'react-router-dom';

const MainPage = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리
    const [userName, setUserName] = useState(''); // 하드코딩된 유저 이름
    const [userEmail, setUserEmail] = useState(''); // 유저 이메일
    const [userToken, setUserToken] = useState(''); // 유저 토큰
    const [mealData, setMealData] = useState(null); // 식단 데이터
    const [nowDate, setNowDate] = useState('');
    const { login } = useParams(false);
    const [isGest, setIsGest] = useState(true); // 게스트 유무
    const [testData, setTestData] = useState(null);

    useEffect(() => {
        const getNowDate = () => {
            const now = new Date();
            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
            const day = String(now.getDate()).padStart(2, '0'); // 날짜를 2자리로 맞추기

            setNowDate(`${year}-${month}-${day}`);
        };

        getNowDate();

        //setIsGest(!login);

        console.log("게스트 : ", isGest)

    }, []);


    useEffect(() => {

        const handleGetUser = async () => {

            console.log("로그인을 시도합니다.")

            const token = localStorage.getItem("token");
            const email = localStorage.getItem("email");

            if (token && email) {
                try {
                    const response = await fetch(`http://3.37.64.39:8000/api/users?email=${email}`, {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": token,
                        }
                    });

                    const result = await response.json();

                    if (response.status === 200) {
                        console.log("로그인 성공 및 데이터 저장")
                        setUserName(result.name);
                        setIsLoggedIn(true);
                        setIsGest(false);
                        setUserEmail(email);
                        setUserToken(token);
                    } else {
                        console.log("로그인 실패: ", result.message);
                        alert("로그인 실패: " + result.message);
                    }
                } catch (error) {
                    console.error("Fetch error: ", error);
                }
            }
        };


        if (!isGest) {
            handleGetUser();
        }

    });


    useEffect(() => {

        const handleGetOfflineDiet = async () => {

            console.log("오프라인 식단을 불러옵니다.")

            const token = localStorage.getItem("token");

            console.log("오프라인 상태")
            const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/read/user@naver.com`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": token,
                }
            });

            const result = await response.json();

            if (response.status === 200) {
                console.log("오프라인 식단 1 : ", result.dailyDiets);
                console.log("오프라인 식단 불러오기 성공")
                console.log("테스트1 : ", testData);
                setMealData(result.dailyDiets);
                setTestData(result.dailyDiets)
                console.log("오프라인 식단 2 : ", mealData);
                console.log("테스트 : ", testData);
            } else {
                console.log("오프라인 식단 불러오기 실패");
                alert("오프라인 식단 실패: " + result.message);
            }
        }

        if (isGest) {
            handleGetOfflineDiet();
        }

    })



    // 개인 식단 조회
    useEffect(() => {

        const handleGetUserDiet = async () => {

            console.log("해당 유저의 식단을 불러옵니다.")

            if (isLoggedIn) {
                console.log("로그인 됨")
                const response = await fetch(`http://3.37.64.39:8000/api/userMeal/weekly/read/${userEmail}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": userToken,
                    }
                });

                const result = await response.json();

                if (response.status === 200) {
                    console.log(result.dailyDiets);
                    console.log("식단 불러오기 성공")
                    setMealData(result.dailyDiets);
                } else {
                    console.log("식단 불러오기 실패");
                    alert("식단 실패: " + result.message);
                }
            }
            else {
                console.log("로그인 안 됨")
            }
            console.log("유저 이메일 =", userEmail)
        }

        if (isLoggedIn) {
            handleGetUserDiet();
        }
    }, [isLoggedIn]);

    useEffect(() => {
        console.log("오프라인 식단 : ", mealData)
    })


    if (!mealData) {
        return <div style={{ width: "100%", height: "100%", display: 'flex', alignItems: "center", justifyContent: 'center' }}><Spin size="large" /></div>;
    }


    return (
        <>
            <Header />
            <div className="swiper-container">
                <Swiper
                    spaceBetween={50}
                    slidesPerView={1}
                    navigation
                    pagination={{ clickable: true }}
                    autoplay={{ delay: 3000, disableOnInteraction: false }}
                    modules={[Navigation, Pagination, Autoplay]}
                    style={{ height: '23vh' }}
                >
                    <SwiperSlide className="slide-content">
                        <img src={logo} className="logoImage" alt="logo" />
                    </SwiperSlide>
                    <SwiperSlide className="slide-content">
                        <img src={cat} className="cat" alt="cat" />
                    </SwiperSlide>
                    <SwiperSlide className="slide-content">
                        <img src={cat} className="cat" alt="cat" />
                    </SwiperSlide>
                    <SwiperSlide className="slide-content">
                        <img src={food} className="cat" alt="food" />
                    </SwiperSlide>
                </Swiper>
            </div>

            {/* 비회원이거나 식단을 신청하지 않은 사람 */}
            {isGest == true ?
                <div className="user-food-detail">
                    <Swiper
                        spaceBetween={50}
                        slidesPerView={1}
                        navigation
                        pagination={{ clickable: true }}
                        modules={[Navigation, Pagination]}
                        className="food-slide"
                    >
                        {mealData.map((data, index) => (
                            <SwiperSlide key={index} className="slide-content1">
                                <Meals mealData={data} userName={userName} isLoggedIn={isLoggedIn} />
                            </SwiperSlide>
                        ))}
                    </Swiper>
                </div>
                :
                <div className="user-food-detail">
                    <h4 style={{ textAlign: 'center', marginTop: 15 }}>{isLoggedIn ? `오늘 ${userName}님의 식단` : '가장 많이 선택한 식단'}</h4>
                    {mealData.map((data, index) => (
                        <div>
                            {data.date == nowDate ?
                                <SwiperSlide key={index} className="slide-content1">
                                    <Meals
                                        mealCardData={data}
                                        userName={userName}
                                        isLoggedIn={isLoggedIn}
                                    />
                                </SwiperSlide> :
                                <>
                                </>
                            }
                        </div>
                    ))}
                </div>
            }

            <BottomNav />
        </>
    );
}

export default MainPage;
