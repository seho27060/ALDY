import StudyJoinItem from "./StudyJoinItem";
import { useState, useEffect } from "react";
import { getStudyMember } from "../../api/study";

const StudyJoin = (props) => {
  const [studyJoinMember, setJoinStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    getStudyMember(props.id).then((res) => {
      setJoinStudyMember(res.data.appliedMember);
    });
  }, []);

  return (
    <div className="study-list-box">
      {studyJoinMember?.map((item, i) => (
        <StudyJoinItem key={i} item={item} num={i} />
      ))}
    </div>
  );
};

export default StudyJoin;
