import React from "react"

import "./css/index.css"
import List from "./List/List";
import Profile from "./Profile/Profile";

const Header = () => {
    return (
        <>
            <div className="header">
            <div className="container">
                    <div className="header-block">
                        <div className="header-block_left">
                            <div className="header-block_left__logo">
                                <img src="https://zarya.by/assets/img/blog/cringe-01.jpg" alt=""/>
                            </div>
                            <List/>
                        </div>
                        <div className="header-block_right">
                            <Profile/>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}


export default Header