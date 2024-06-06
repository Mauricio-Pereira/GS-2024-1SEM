import React from 'react';
import Image from 'next/image';
import Link from 'next/link';

import './header_style.css';

const Header = () => {
  return (
    <header className="header">
      <div className="container">
        <div className="logo">
          <Link href="/" className="blue-logo"><h1>Blue Horizon</h1></Link>
        </div>
        <nav className="links-nav">
          <ul className="links-list">
            <li><Link href="/">Home</Link></li>
            <li><Link href="/products">Produtos</Link></li>
            <li><Link href="/ongs">ONG's</Link></li>
            <li><Link href="/about">Sobre</Link></li>
          </ul>
        </nav>
        <nav className="info-nav">
          <ul className="info-list">
            <li><Link href="/login-page" className="login-button"><i className="fab fa-user-profile"></i>Login</Link></li>
            <li><Link href="/sign-in-page" className="sign-in-button">Cadastre-se</Link></li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
