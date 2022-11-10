import { useState, useEffect } from "react";

import { getStudyMember } from "../../api/study";
import StudyJoinItem from "./StudyJoinItem";

const StudyJoin = (props) => {
  const [studyJoinMember, setJoinStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    console.log(props.id, "가입신청페이지입니다~");
    getStudyMember(props.id).then((res) => {
      console.log(res.data.appliedMember);
      setJoinStudyMember(res.data.appliedMember);
    });
    console.log(studyJoinMember, "여기는 누구니");
    // 이 밑에는 지우기
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
