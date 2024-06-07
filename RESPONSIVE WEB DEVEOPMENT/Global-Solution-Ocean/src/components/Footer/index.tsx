import React from 'react';
import Link from 'next/link';

import './footer_style.css';
  
const Footer = () => {
  return (
    <footer className="footer">
      <div className="container">
        <div className="boxes">
          <div className="site-info">
            <h2>Sobre Nós</h2>
            <p>Na BlueCommerce Tech, acreditamos que o comércio online pode ser mais do que uma transação simples - pode ser uma oportunidade para fazer a diferença. Com a Blue Horizon Market, buscamos unir consumidores conscientes e empresas comprometidas com a sustentabilidade. Nosso objetivo é criar um ambiente onde cada compra conte uma história de impacto positivo, desde a redução do plástico nos oceanos até o apoio às comunidades costeiras.</p>
          </div>
          <div className="useful-links">
            <h2>Links Úteis</h2>
            <ul>
              <li><Link href="/">Home</Link></li>
              <li><Link href="/products">Produtos</Link></li>
              <li><Link href="/ongs">Ongs</Link></li>
              <li><Link href="/about">Sobre Nós</Link></li>
            </ul>
          </div>
          <div className="useful-links">
            <h2>Integrantes</h2>
            <ul>
              <li><a href="https://github.com/VitorOnofreRamos">Vitor Onofre Ramos - RM553241</a></li>
              <li><a href="https://github.com/Mauricio-Pereira">Mauricio Vieira Pereira - RM553748</a></li>
              <li><a href="https://github.com/Luiz1614">Luiz Otávio Leitão Silva - RM553542</a></li>
            </ul>
          </div>
        </div>
        <div className="copyright">
          <div>
            <p>&copy; {new Date().getFullYear()} Blue Horizon. Todos os direitos reservados.</p>
          </div>
        </div>
      </div>
    </footer>
  );
};
  
export default Footer;
