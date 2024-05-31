import Item from "../Sections/ProductsSection/interface";

import Image from "next/image";
import React from "react";
import Link from "next/link";
import "./product_style.css";
import { deflate } from "zlib";

interface ProductProps {
  title: string;
  image: string;
  link: string;
  price: number;
}

{/*const Product: React.FC<ProductProps> = ({ title, image, link, price }) => {
  return (
    <div className="product-card">
      <Link className="link-card"
        href={link}
        target="_blank"
        rel="noopener noreferrer"
      >
        <div className="product-content">
          <h2 className="product-title">{title}</h2>
          <Image className="product-image" src={image} width={200} height={200} alt={"Nome do Produto " + title} />
          <span className="product-preco">R$ {price}</span>
        </div>
      </Link>
    </div>
  );
};
*/}

const Product: React.FC<Item> = ({id, title, body}) => {
  return(
    <div key={id} className="product-card">
      <Link className="link-card"
        href="#"
        target="_blank"
        rel="noopener noreferrer"
      >
        <div className="product-content">
          <h2 className="product-title">{title}</h2>
          <p className="product-description">{body}</p>
        </div>
      </Link>
    </div>
  )
}

export default Product;