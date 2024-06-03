'use client';

import {useState, useEffect} from "react";
import Product from '@/components/Product';
import '../sections_style.css';

import Item from "./interface";

export default function ProductsSection() {
    const [data, setData] = useState<Item[]>([]);

    useEffect(() => {
        // Função assíncrona para buscar dados da API
        const getDataServer = async () => {
            try {
                const response = await fetch("https://jsonplaceholder.typicode.com/posts");
                const content = await response.json();
                setData(content);
            } catch (error) {
                console.error("Erro ao buscar dados:", error);
            }
        };

        // Chamando a função assíncrona
        getDataServer();
    }, []);

    return(
        <>
        <section className="section products-section">
            <div className="container">
                <div className="search-box">
                    <p>Teste</p>
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
