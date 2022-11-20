import { useState, useEffect } from "react";

import { getStudyMember } from "../../api/study";
import StudyMemberItem from "./StudyMemberItem";

const StudyMember = (props) => {
  const [studyMember, setStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    console.log(props.id, "스터디 아이디를 알려주세요 제발~");
    getStudyMember(props.id).then((res) => {
      console.log(res.data, "스터디 멤버의 정보를 알려주세요");
      setStudyMember(res.data.registeredMember);
    });
    console.log(studyMember, "제발제발요");
  }, []);

  return (
    <div className="study-list-box">
      {studyMember?.map((item, i) => (
        <StudyMemberItem key={i} item={item} num={i} />
      ))}
    </div>
  );
};

export default StudyMember;
