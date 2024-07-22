import React from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png'

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons'

const Header = () => {
    return (
        <header className="header">
            <div className="contents">
                <div>
                    {/* back : 고객 이름 */}
                    OOO님!
                </div>
                <div>
                    <img src={logo} className="logoImage"/>
                </div>
                <nav className="navigation">
                    <ul>
                        <li>
                            <FontAwesomeIcon icon={faBell} size="2x" />
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faFileLines} size="2x" />
                        </li>
                    </ul>
                </nav>
            </div>
        </header>
    );
};

export default Header;
