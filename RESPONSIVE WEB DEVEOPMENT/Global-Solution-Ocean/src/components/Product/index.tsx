import Item from "../Sections/ProductsSection/interface";

import Image from "next/image";
import React from "react";
import Link from "next/link";
import "./product_style.css";
import { deflate } from "zlib";

const Product: React.FC<Item> = ({id, description, name, price, stock}) => {
  return (
    <div className="product-card" id={id}>
      <Link className="link-card"
        href="#"
        target="_blank"
        rel="noopener noreferrer"
      >
        <div className="product-content">
          <h2 className="product-name">{name}</h2>
          <div className="product-image">
          </div>
          <p className="product-description">{description}</p>
          <div className="product-values">
            <span className="product-price">R$ {price}</span>
            <span className="product-stock">{stock} unidades</span>
          </div>
        </div>
      </Link>
    </div>
  );
};


export default Product;