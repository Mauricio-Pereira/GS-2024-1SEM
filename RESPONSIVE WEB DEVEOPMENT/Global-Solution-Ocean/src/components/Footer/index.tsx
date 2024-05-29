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
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc nec tincidunt interdum, justo quam cursus mi.</p>
          </div>
          <div className="useful-links">
            <h2>Links Úteis</h2>
            <ul>
              <li><Link href="/">Link 1</Link></li>
              <li><Link href="/">Link 2</Link></li>
              <li><Link href="/">Link 3</Link></li>
            </ul>
          </div>
          <div className="social-media">
            <h2>Redes Sociais</h2>
            <ul>
              <li><a href="#"><i className="fab fa-facebook-f"></i><span>Facebook</span></a></li>
              <li><a href="#"><i className="fab fa-twitter"></i><span>Twiter</span></a></li>
              <li><a href="#"><i className="fab fa-instagram"></i><span>Intagram</span></a></li>
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