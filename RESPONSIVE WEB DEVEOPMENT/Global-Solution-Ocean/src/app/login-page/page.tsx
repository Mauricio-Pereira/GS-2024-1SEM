import ImageSection from "@/components/Sections/ImageSection";
import LoginSection from "@/components/Sections/LoginSection";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import Image from "next/image";

export default function LoginPage() {
  return (
    <main className="flex flex-row">
      <ImageSection
        src="/images/Logo.png"
        alt="Logo.png"
        width={100}
        height={100}
        style=""
      />
      <LoginSection/>
    </main>
  );
}
