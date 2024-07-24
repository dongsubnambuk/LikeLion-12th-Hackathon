import React from "react";
import '../CSS/Header.css';
import logo from '../images/logo.png'
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell, faFileLines } from '@fortawesome/free-regular-svg-icons'

const Header = () => {
    return (
        <header>
            <div className="contents">
                <div className="header_contents">
                    {/* back : 고객 이름 */}
                    OOO님
                </div>

                <div className="header_contents">
                    <img src={logo} className="logoImage" alt="logo" />
                </div>

                <div className="header_contents">
                    <ul>
                        <li>
                            <FontAwesomeIcon icon={faBell} size="2x" />
                        </li>
                        <li>
                            <FontAwesomeIcon icon={faFileLines} size="2x" />
                        </li>
                    </ul>
                </div>
            </div>
        </header>
    );
};

export default Header;
