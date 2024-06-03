import React from 'react';
import Image from 'next/image';
import Link from 'next/link';

import './header_style.css';

const Header = () => {
  return (
    <header className="header">
      <div className="container">
        <div className="logo">
          <h1>Blue Horizon</h1>
        </div>
        <nav className="navigation">
          <ul className="links-list">
            <li><Link href="/">Home</Link></li>
            <li><Link href="/products">Produtos</Link></li>
            <li><Link href="/about">Sobre</Link></li>
            <li><Link href="/contact">Contato</Link></li>
          </ul>
        </nav>
        <div className="user-info">
          <Link href="/sign-in-page" className="sign-in-button">Cadastre-se</Link>
          <Link href="/login-page" className="login-button">Login</Link>
        </div>
      </div>
    </header>
  );
};

export default Header;
