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
                const response = await fetch("http://localhost:8082/bluehorizon/products");
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
                    <label htmlFor="search">Digite o Produto que deseja procurar</label>
                    <input
                        type="text" id="search"
                        placeholder="Pesquisar produto..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
                <div className="products-box">
                    {data.filter(item =>
                        item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                        item.description.toLowerCase().includes(searchTerm.toLowerCase())
                    ).map((item: Item) => (
                        <Product
                            id={item.id}
                            name={item.name}
                            description={item.description}
                            price={item.price}
                            stock={item.stock}
                        />
                    ))}
                </div>
            </div>
        </section>
        </>
    );
};
