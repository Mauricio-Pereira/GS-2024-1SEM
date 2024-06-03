'use client';

import {useState, useEffect} from "react";
import Product from '@/components/Product';
import '../sections_style.css';

import Item from "./interface";

export default function ProductsSection() {
    const [data, setData] = useState<Item[]>([]);
    const [searchTerm, setSearchTerm] = useState(''); // Estado para armazenar o termo de pesquisa

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
                    <input
                        type="text"
                        placeholder="Pesquisar produto..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
                <div className="products-box">
                    {data.filter(item =>
                        item.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                        item.body.toLowerCase().includes(searchTerm.toLowerCase())
                    ).map((item: Item) => (
                        <Product
                        key={item.id}
                        userId={item.userId}
                        id={item.id}
                        title={item.title}
                        body={item.body}
                        />
                    ))}
                </div>
            </div>
        </section>
        </>
    );
};
