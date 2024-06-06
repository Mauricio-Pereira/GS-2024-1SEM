import ParagraphSection from "@/components/Sections/ParagraphSection";
import ImageSection from "@/components/Sections/ImageSection";

export default function Products() {
  return (
    <main className="flex">
      <ParagraphSection
        text="Teste"
        style="no-bg"
      />
      <section className="flex flex-row">
        <ParagraphSection
          text="Ongs"
        />
        <ImageSection
          src="/images/Logo.png"
          alt="Logo.png"
          width={100}
          height={100}
        />
      </section>
      <section className="flex flex-row">
      <ImageSection
          src="/images/Logo.png"
          alt="Logo.png"
          width={100}
          height={100}
        />
        <ParagraphSection
          text="Ongs"
          style="no-bg"
        />
      </section>
    </main>
  );
}
