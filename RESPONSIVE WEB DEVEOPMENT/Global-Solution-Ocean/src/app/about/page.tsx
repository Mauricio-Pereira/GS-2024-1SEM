import ParagraphSection from "@/components/Sections/ParagraphSection";
import Image from "next/image";

export default function About() {
  return (
    <main className="flex">
      <ParagraphSection 
        title="Sobre Nós"
        text="Desenvolver uma plataforma de e-commerce que promova a sustentabilidade, com 12% das vendas sendo destinadas a ONGs que atuam na limpeza dos oceanos (8%) e para a manutenção do site (4%).
        Oferecer às empresas vendedoras a oportunidade de participar de campanhas de marketing gratuitas ao atingirem metas de vendas.
        Utilizar tecnologias modernas para criar uma experiência de usuário eficiente e atrativa."
        style=""
      />
    </main>
  );
}
