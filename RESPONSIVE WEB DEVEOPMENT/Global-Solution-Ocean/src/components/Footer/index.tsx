import React from 'react';
import Link from 'next/link';

import './footer_style.css';

interface FooterProps {
    // Adicione aqui quaisquer props que você possa precisar
}
  
const Footer: React.FC<FooterProps> = () => {
  return (
    <footer className="footer">
      <div className="container">
        <div className="row">
          <div className="col">
            <h2>Sobre Nós</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc nec tincidunt interdum, justo quam cursus mi.</p>
          </div>
          <div className="col">
            <h2>Links Úteis</h2>
            <ul>
              <li><a href="#">Link 1</a></li>
              <li><a href="#">Link 2</a></li>
              <li><a href="#">Link 3</a></li>
            </ul>
          </div>
          <div className="col">
            <h2>Redes Sociais</h2>
            <ul>
              <li><a href="#"><i className="fab fa-facebook-f"></i></a></li>
              <li><a href="#"><i className="fab fa-twitter"></i></a></li>
              <li><a href="#"><i className="fab fa-instagram"></i></a></li>
            </ul>
          </div>
        </div>
        <div className="row">
          <div className="col">
            <p>&copy; {new Date().getFullYear()} Nome da Empresa. Todos os direitos reservados.</p>
          </div>
        </div>
      </div>
    </footer>
  );
};
  
export default Footer;