'use client';

import {useState, useEffect} from "react";
import Product from '@/components/Product';
import '../sections_style.css';

import Item from "./interface";

export default function ProductsSection() {
    const [data, setData] = useState([]);

    function getDataServer() {
        fetch("https://jsonplaceholder.typicode.com/posts")
        .then((response) => {
            return response.json();
        })
        .then((content) => {
            setData(content);
        });
    }

    return(
        <>
        <section className="section products-section">
            <div className="container">
                <div className="search-box">
                    <p>Teste</p>
                    <button
                        onClick={getDataServer}
                        className="border bg-amber-950 p-4 text-white"
                    >
                    Carregar
                    </button>
                </div>
                <div className="products-box">
                    {data.map((item: Item) => {
                        return (
                            <Product
                                userId={item.userId}
                                id={item.id}
                                title={item.title}
                                body={item.body}
                            />
                        );
                })}
                </div>
            </div>
        </section>
        </>
    );
};
