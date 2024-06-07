import ImageSection from "@/components/Sections/ImageSection";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import Image from "next/image";

export default function About() {
  return (
    <main className="flex">
      <ParagraphSection 
        title="Sobre Nós"
        text="Na BlueCommerce Tech, acreditamos que o comércio online pode ser mais do que uma transação simples - pode ser uma oportunidade para fazer a diferença. Com a Blue Horizon Market, buscamos unir consumidores conscientes e empresas comprometidas com a sustentabilidade. Nosso objetivo é criar um ambiente onde cada compra conte uma história de impacto positivo, desde a redução do plástico nos oceanos até o apoio às comunidades costeiras."
        style="no-bg"
      />
      <section className="flex flex-row">
        <ParagraphSection
          title="Nossa Missão"
          text="Nossa missão na BlueCommerce Tech é transformar o comércio online em uma força para o bem. Através da Blue Horizon Market, estamos empenhados em promover a sustentabilidade, apoiar organizações dedicadas à limpeza dos oceanos e oferecer uma experiência de compra que vai além do simples ato de adquirir produtos. Estamos aqui para criar um impacto positivo duradouro, uma compra de cada vez."
        />
        <ParagraphSection
          title="Como Funciona"
          text="Na Blue Horizon Market, a sustentabilidade é tecida em cada aspecto da nossa plataforma. Empresas comprometidas com práticas ambientalmente responsáveis podem listar seus produtos em nossa loja online. Cada compra feita através do nosso site contribui automaticamente com 12% do valor total, sendo 8% destinados a ONGs que trabalham na preservação dos oceanos e 4% para a manutenção contínua da nossa plataforma. Além disso, oferecemos campanhas de marketing gratuitas para empresas que atingem metas de vendas, promovendo uma cultura de colaboração e impacto positivo."
        />
      </section>
      <section className="flex flex-row">
      <ImageSection
          src="/images/Logo.png"
          alt="Logo.png"
          width={100}
          height={100}
          style="no-bg"
        />
        <ParagraphSection
          title="Nossos Compromissos"
          text="Na BlueCommerce Tech, a transparência é fundamental. Estamos comprometidos em fornecer relatórios detalhados sobre o impacto ambiental de cada compra realizada em nossa plataforma. Acreditamos que é importante que você saiba exatamente como suas escolhas estão fazendo a diferença no mundo. Além disso, nos esforçamos para atender aos mais altos padrões de qualidade e serviço, garantindo que sua experiência conosco seja sempre excepcional."
        />
      </section>
    </main>
  );
}
