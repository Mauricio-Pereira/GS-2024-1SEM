import Image from "next/image";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import HeaderSection from "@/components/Sections/HeaderSection";
import ImageSection from "@/components/Sections/ImageSection";

export default function Home() {
  return (
    <main className="flex home-page">
      <HeaderSection/>
        <section className="flex flex-row">
        <ParagraphSection 
          text="Bem-vindo ao Blue Horizon Market, onde o seu poder de compra se torna uma força para a sustentabilidade dos oceanos. Estamos comprometidos em oferecer uma plataforma de e-commerce inovadora que não apenas simplifica suas compras, mas também ajuda a preservar nosso precioso ecossistema marinho. Junte-se a nós nessa jornada para um futuro mais verde e próspero."
          style="no-bg"
        />
        <ImageSection
          src="/images/Logo.png"
          alt="Logo.png"
          width={100}
          height={100}
        />
        </section>
    </main>
  );
}
