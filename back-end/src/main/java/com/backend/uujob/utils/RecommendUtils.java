package com.backend.uujob.utils;

import com.backend.uujob.entity.Active;
import com.backend.uujob.entity.Job;
import com.backend.uujob.entity.Position;
import com.backend.uujob.entity.UserSimilarity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mapleleaf
 * @date 2023年05月31日15:43
 */
public class RecommendUtils {

    /**
     * 将用户的点击行为组装成一个map,key为userId，value也是一个map，这个map记录的是二级类目以及它对应的点击量
     * @param userActiveList 用户的购买行为列表
     * @return 组装好的用户的购买行为的map集合
     */
    public static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> assembleUserBehavior(List<Active> userActiveList) {
        ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> activeMap = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>>();
        // 遍历查询到的用户点击行为数据
        for (Active userActive : userActiveList) {
            // 1.获取用户id
            Integer userId = userActive.getUserId();
            // 2.获取职位种类id
            Integer positionId = userActive.getPositionId();
            // 3.获取该职位种类的点击量
            Integer hits = userActive.getHits();

            // 判断activeMap中是否已经存在了该userId的信息，如果存在则进行更新
            if (activeMap.containsKey(userId)) {
                ConcurrentHashMap<Integer, Integer> tempMap = activeMap.get(userId);
                tempMap.put(positionId, hits);
                activeMap.put(userId, tempMap);
            } else {
                // 不存在则直接put进
                ConcurrentHashMap<Integer, Integer> positionMap = new ConcurrentHashMap<Integer, Integer>();
                positionMap.put(positionId, hits);
                activeMap.put(userId, positionMap);
            }
        }

        return activeMap;
    }

    /**
     * 计算用户与用户之间的相似性，返回计算出的用户与用户之间的相似度对象
     * @param activeMap 用户对各个职位类别的点击行为的一个map集合
     * @return 计算出的用户与用户之间的相似度的对象存储形式
     */
    public static List<UserSimilarity> calcSimilarityBetweenUsers(ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> activeMap) {
        // 用户之间的相似度对集合
        List<UserSimilarity> similarityList = new ArrayList<UserSimilarity>();

        // 获取所有的键的集合
        Set<Integer> userSet = activeMap.keySet();

        // 把这些集合放入ArrayList中
        List<Integer> userIdList = new ArrayList<Integer>(userSet);

        // 小于两个说明当前map集合中只有一个map集合的购买行为，或者一个都没有，直接返回
        if (userIdList.size() < 2) {
            return similarityList;
        }

        // 计算所有的用户之间的相似度对
        for (int i = 0; i < userIdList.size() - 1; i++) {
            for (int j = i + 1; j < userIdList.size(); j++) {
                // 分别获取两个用户对每个二级类目的点击量
                ConcurrentHashMap<Integer, Integer> positionMap = activeMap.get(userIdList.get(i));
                ConcurrentHashMap<Integer, Integer> userRefPositionMap = activeMap.get(userIdList.get(j));

                // 获取两个map中二级类目id的集合
                Set<Integer> key1Set = positionMap.keySet();
                Set<Integer> key2Set = userRefPositionMap.keySet();
                Iterator<Integer> it1 = key1Set.iterator();
                Iterator<Integer> it2 = key2Set.iterator();

                // 两用户之间的相似度
                double similarity = 0.0;
                // 余弦相似度公式中的分子
                double molecule = 0.0;
                // 余弦相似度公式中的分母
                double denominator = 1.0;
                // 余弦相似度公式中分母根号下的两个向量的模的值
                double vector1 = 0.0;
                double vector2 = 0.0;

                while (it1.hasNext() && it2.hasNext()) {
                    Integer it1Id = it1.next();
                    Integer it2Id = it2.next();
                    // 获取二级类目对应的点击次数
                    Integer hits1 = positionMap.get(it1Id);
                    Integer hits2 = userRefPositionMap.get(it2Id);
                    // 累加分子
                    molecule += hits1 * hits2;
                    // 累加分母中的两个向量的模
                    vector1 += Math.pow(hits1, 2);
                    vector2 += Math.pow(hits2, 2);
                }
                // 计算分母
                denominator = Math.sqrt(vector1) * Math.sqrt(vector2);
                // 计算整体相似度
                similarity = molecule / denominator;

                // 创建用户相似度对对象
                UserSimilarity userSimilarity = new UserSimilarity();
                userSimilarity.setUserId(userIdList.get(i));
                userSimilarity.setRefUserId(userIdList.get(j));
                userSimilarity.setSimilarity(similarity);
                // 将计算出的用户以及用户之间的相似度对象存入list集合
                similarityList.add(userSimilarity);
            }
        }

        return similarityList;
    }

    /**
     * 找出与userId查看岗位行为最相似的topN个用户
     * @param userId 需要参考的用户id
     * @param userSimilarityList 用户相似度列表
     * @param topN 与userId相似用户的数量
     * @return 与userId最相似的topN个用户
     */
    public static List<Integer> getSimilarityBetweenUsers(Integer userId, List<UserSimilarity> userSimilarityList, Integer topN) {
        // 用来记录与userId相似度最高的前N个用户的id
        List<Integer> similarityList = new ArrayList<Integer>(topN);

        // 堆排序找出最高的前N个用户，建立小根堆，遍历的时候当前的这个相似度比堆顶元素大就剔掉堆顶的值，把这个数入堆(把小的都删除干净,所以要建立小根堆)
        PriorityQueue<UserSimilarity> minHeap = new PriorityQueue<UserSimilarity>(new Comparator<UserSimilarity>(){
            @Override
            public int compare(UserSimilarity o1, UserSimilarity o2) {
                if (o1.getSimilarity() - o2.getSimilarity() > 0) {
                    return 1;
                } else if (o1.getSimilarity() - o2.getSimilarity() == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        for (UserSimilarity userSimilarityDTO : userSimilarityList) {
            if (minHeap.size() < topN) {
                minHeap.offer(userSimilarityDTO);
                System.out.println(minHeap.peek().getSimilarity());
            } else if (minHeap.peek().getSimilarity() < userSimilarityDTO.getSimilarity()) {
                minHeap.poll();
                minHeap.offer(userSimilarityDTO);
            }
        }
        // 把得到的最大的相似度的用户的id取出来(不要取它自己)
        for (UserSimilarity userSimilarityDTO : minHeap) {
            similarityList.add(userSimilarityDTO.getUserId() == userId ? userSimilarityDTO.getRefUserId() : userSimilarityDTO.getUserId());
        }

        return similarityList;
    }

    /**
     * 到similarUserList中的用户访问的职位类别中查找userId不经常点击的职位类别中获得被推荐的类目id
     * @param userId 被推荐职位类别的用户id
     * @param similarUserList 用userId相似的用户集合
     * @param userActiveList 所有用户的浏览行为
     * @return 可以推荐给userId的职位类别id列表
     */
    public static List<Integer> getRecommendPosition(Integer userId, List<Integer> similarUserList, List<Active> userActiveList) {
        List<Integer> recommendProductList = new ArrayList<Integer>();

        // userId的浏览行为列表
        List<Active> userIdActiveList = findUsersBrowsBehavior(userId, userActiveList);

        // 对userId的浏览行为按照职位类别id排个序，方便后续与推荐的用户的浏览行为中的职位类别的点击次数直接相减，避免时间复杂度为O(n2)
        Collections.sort(userIdActiveList, new Comparator<Active>(){
            @Override
            public int compare(Active o1, Active o2) {
                return o1.getPositionId().compareTo(o2.getPositionId());
            }
        });

        // 1.从与useId浏览行为相似的每个用户中找出一个推荐的职位类别
        for (Integer refId : similarUserList) {
            // 计算当前用户所点击的二级类目次数与被推荐的用户所点击的职位类别的次数的差值
            // 找到当前这个用户的浏览行为
            List<Active> currActiveList = findUsersBrowsBehavior(refId, userActiveList);

            // 排序，同上述理由
            Collections.sort(currActiveList, new Comparator<Active>(){
                @Override
                public int compare(Active o1, Active o2) {
                    return o1.getPositionId().compareTo(o2.getPositionId());
                }
            });

            // 记录差值最大的二级类目的id
            Integer maxPositionId = 0;

            // 记录最大的差值
            double maxDifference = 0.0;
            for (int i = 0; i < currActiveList.size(); i++) {
                // 求出点击量差值最大的二级类目，即为要推荐的类目
                double difference = Math.abs(currActiveList.get(i).getHits() - userIdActiveList.get(i).getHits());
                if (difference > maxDifference) {
                    maxDifference = difference;
                    maxPositionId = currActiveList.get(i).getPositionId();
                }
            }
            recommendProductList.add(maxPositionId);
        }
        return recommendProductList;
    }

    /**
     * 找到当前用户的浏览行为列表
     * @param userId 当前用户id
     * @param userActiveList 所有用户的浏览行为列表
     * @return 当前用户的浏览行为列表
     */
    public static List<Active> findUsersBrowsBehavior(Integer userId, List<Active> userActiveList) {
        List<Active> currActiveList = new ArrayList<Active>();
        for (Active userActiveDTO : userActiveList) {
            if (userActiveDTO.getUserId().equals(userId)) {
                currActiveList.add(userActiveDTO);
            }
        }
        return currActiveList;
    }

    /**
     * 找到当前职位列表中点击量最高的职位列表
     * @param jobList 职位列表
     * @return 点击量最高的职位
     * 暂未统计Job的点击数
     */
//    public static Job findMaxHitsJob(List<? extends Job> jobList) {
//        if (jobList == null || jobList.size() == 0) {
//            return null;
//        }
//        // 记录当前最大的点击量
//        Integer maxHits = 0;
//
//        // 记录当前点击量最大的职位类别
//        Job job = null;
//        for (Job temp : jobList) {
//            if (temp.getHits() >= maxHits) {
//                maxHits = temp.getHits();
//                job = temp;
//            }
//        }
//        return job;
//    }

}