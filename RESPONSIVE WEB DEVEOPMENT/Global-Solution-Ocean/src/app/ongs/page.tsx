import ParagraphSection from "@/components/Sections/ParagraphSection";
import ImageSection from "@/components/Sections/ImageSection";

export default function Ongs() {
  return (
    <main className="flex">
      <ParagraphSection
        title="Ong's e Parceiros"
        style="no-bg"
      />
      <ImageSection
        src=""
        alt=""
        width={100}
        height={100}
      />
      <section className="grid grid-cols-4 px-8 py-3 bg-[#fffdf6]">
        <ImageSection
          src="/svg/catedra.svg"
          alt="catedra.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/aws-partner-logo.svg"
          alt="aws-partner-logo.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/o2o.svg"
          alt="o2o.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/rede-oceano-limpo-logo.svg"
          alt="rede-oceano-limpo-logo.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/select.svg"
          alt="select.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/softtek-logo.svg"
          alt="softtek-logo.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/unesco.svg"
          alt="unesco.svg"
          width={100}
          height={100}
        />
        <ImageSection
          src="/svg/pacto-global.svg"
          alt="pacto-global.svg"
          width={100}
          height={100}
        />
      </section>
    </main>
  );
}
