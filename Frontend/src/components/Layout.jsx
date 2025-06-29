// src/components/Layout.js
import Header from './Header';
import BottomNav from './BottomNav';

const HEADER_HEIGHT = 64;
const BOTTOMNAV_HEIGHT = 70;

const Layout = ({ children, notificationCount, surveyCount, isLoggedIn, userInfo }) => (
  <div className="app-layout">
    <Header 
      notificationCount={notificationCount} 
      surveyCount={surveyCount}
      isLoggedIn={isLoggedIn}
      userInfo={userInfo}
    />
    <main
      className="app-main"
      style={{
        paddingTop: HEADER_HEIGHT,
        paddingBottom: BOTTOMNAV_HEIGHT,
        minHeight: `calc(100vh - ${HEADER_HEIGHT + BOTTOMNAV_HEIGHT}px)`,
        boxSizing: 'border-box',
      }}
    >
      {children}
    </main>
    <BottomNav />
  </div>
);

export default Layout;
