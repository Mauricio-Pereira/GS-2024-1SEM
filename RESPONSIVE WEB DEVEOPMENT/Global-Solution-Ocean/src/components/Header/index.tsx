import React from 'react';
import Link from 'next/link';

import './header_style.css';

interface HeaderProps {
  // Adicione aqui quaisquer props que você possa precisar
}

const Header: React.FC<HeaderProps> = () => {
  return (
    <header className="header">
      <div className="logo-container">
        <img src="/path/to/logo.png" alt="Logo da Empresa" />
      </div>
      <nav className="navigation">
        <ul>
          <li><Link href="#home">Home</Link></li>
          <li><Link href="#about">Sobre</Link></li>
          <li><Link href="#services">Serviços</Link></li>
          <li><Link href="#contact">Contato</Link></li>
        </ul>
      </nav>
      <div className="search-bar">
        <input type="text" placeholder="Pesquisar..." />
        <button>Buscar</button>
      </div>
      <button className="menu-toggle">
        Menu
      </button>
    </header>
  );
};

export default Header;
