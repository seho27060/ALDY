import StudyMemberItem from "./StudyMemberItem";
import { useState, useEffect } from "react";
import { getStudyMember } from "../../api/study";

const StudyMember = (props) => {
  const [studyMember, setStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    getStudyMember(props.id).then((res) => {
      setStudyMember(res.data.registeredMember);
    });
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
