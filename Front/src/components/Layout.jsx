// Layout.js
import Header from './Header';
import BottomNav from './BottomNav';

const HEADER_HEIGHT = 64;    // Header 높이(px)
const BOTTOMNAV_HEIGHT = 70; // BottomNav 높이(px)

const Layout = ({ children }) => (
  <div className="app-layout">
    <Header />
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
