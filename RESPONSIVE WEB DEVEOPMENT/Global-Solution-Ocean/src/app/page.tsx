import Image from "next/image";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import HeaderSection from "@/components/Sections/HeaderSection";
import ImageSection from "@/components/Sections/ImageSection";

export default function Home() {
  return (
    <main className="flex home-page">
      <HeaderSection/>
        <section className="flex border-y-2">
        <ParagraphSection 
          title="Título 1"
          text="Lorem ipsum dolor sit, amet consectetur adipisicing elit. Commodi, iusto sit porro neque cumque delectus nulla ratione pariatur vero nostrum ipsam molestiae, nihil at ab consequatur perspiciatis ipsum quidem magni!
          Itaque cum aspernatur, unde numquam quos magnam! Obcaecati, reprehenderit accusamus quo alias, dignissimos quas repellendus aliquam impedit eligendi, sapiente nisi. Nisi, repellendus aliquid?
          Laudantium accusamus rem tenetur aperiam ipsam quasi blanditiis at quis illo mollitia odio iure totam molestiae eveniet ea, beatae ut commodi eaque optio. Architecto debitis pariatur temporibus ex id consectetur.
          Reprehenderit consequuntur voluptatum voluptates dicta numquam odio obcaecati dignissimos pariatur nesciunt, commodi nobis illum laudantium debitis perferendis, laborum fuga expedita. Quam explicabo similique repellendus repudiandae eum asperiores! Explicabo, maxime ratione?
          Doloribus sunt recusandae sit explicabo provident quasi porro modi possimus ipsum fugit blanditiis deserunt, debitis distinctio ut qui laudantium quam sequi quae dolor quis libero! Aspernatur aut reiciendis placeat impedit.
          Debitis, quo fugiat voluptates cupiditate officia quasi eaque autem nesciunt atque sunt minus inventore magni eveniet dolor accusantium reiciendis officiis eligendi delectus. Labore enim aperiam placeat animi dolore sed incidunt!"
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
