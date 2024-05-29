import React from 'react';
import Link from 'next/link';

import './header_style.css';

const Header = () => {
  return (
    <header className="header">
      <div className="container">
        <div className="logo">
          <img src="/images/logo.png" alt="Logo da Empresa"/>
          <h1>Blue Horizon</h1>
        </div>
        <nav className="navigation">
          <ul className="links">
            <li><Link href="/">Home</Link></li>
            <li><Link href="/products">Produtos</Link></li>
            <li><Link href="/about">Sobre</Link></li>
            <li><Link href="/contact">Contato</Link></li>
          </ul>
        </nav>
        <div className="user-info">
          <Link href="/sign-in-page">Cadastre-se</Link>
          <Link href="/login-page">Login</Link>
        </div>
      </div>
    </header>
  );
};

export default Header;
